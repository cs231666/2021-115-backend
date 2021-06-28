package com.daoyun.demo.service.impl;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daoyun.demo.mapper.CourseMapper;
import com.daoyun.demo.mapper.ParticipateInCourseMapper;
import com.daoyun.demo.mapper.UserMapper;
import com.daoyun.demo.pojo.Course;
import com.daoyun.demo.pojo.ParticipateInCourse;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.dto.CourseDto;
import com.daoyun.demo.pojo.dto.CourseInfo;
import com.daoyun.demo.pojo.dto.CourseMemberInfo;
import com.daoyun.demo.pojo.dto.CourseMemberInfoWithRank;
import com.daoyun.demo.service.ICourseService;
import com.daoyun.demo.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-31
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired(required = false)
    private CourseMapper courseMapper;

    @Autowired(required = false)
    private ParticipateInCourseMapper participateInCourseMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public ReturnInfo createCourse(CourseDto courseDto) {
        Course course = new Course();
        /**
         * 生成班课号
         */
        String code = RandomUtil.randomNumbers(7);
        System.out.println(this.courseMapper.selectList(new QueryWrapper<Course>().eq("course_code", code)));
        /**
         * 对班课号判断是否唯一
         */
        while (!this.courseMapper.selectList(new QueryWrapper<Course>().eq("course_code", code)).isEmpty()) {
            code = RandomUtil.randomNumbers(7);
        }

        /**
         * 存到数据库
         */
        course.setCourseCode(code);
        course.setCourseName(courseDto.getCourseName());
        course.setTeacher(courseDto.getTeacher());
        course.setClassName(courseDto.getClassName());
        course.setOrg(courseDto.getOrg());
        course.setTerm(courseDto.getTerm());
        course.setCreationDate(LocalDateTime.now());
        course.setCreator(courseDto.getCreator());
        this.courseMapper.insert(course);
        return ReturnInfo.success("创建成功");
    }

    @Override
    public ReturnInfo deleteCourseById(Integer courseId) {
        this.courseMapper.deleteById(courseId);
        return ReturnInfo.success("删除成功");
    }

    @Override
    public ReturnInfo updateCourseById(CourseInfo courseInfo) {
        Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("course_code", courseInfo.getCourseCode()));
        course.setStatus(courseInfo.getStatus());
        course.setIsEnd(courseInfo.getIsEnd());
        course.setModificationDate(LocalDateTime.now());
        this.courseMapper.updateById(course);
        return ReturnInfo.success("修改成功");
    }

    /**
     * 考虑是否实现分页查询
     *
     * @return
     */
    @Override
    public ReturnInfo getCourse() {
        List course = this.courseMapper.selectList(new QueryWrapper<>());
        return ReturnInfo.success("查询成功", course);
    }

    @Override
    public ReturnInfo getUCourse(Integer userId) {
        List course = this.courseMapper.getUCourse(userId);
        return ReturnInfo.success("查询成功", course);
    }

    @Override
    public ReturnInfo participateInCourse(String courseCode, Integer userId) {

        /**
         * 判断用户是否已加入该班课
         */
        Course course = this.courseMapper.selectOne(new QueryWrapper<Course>().eq("course_code", courseCode));
        if (course.getCreator().equals(userId)){
            return ReturnInfo.error("不能加入自己的班课");
        }
        if (course.getIsEnd() == 1){
            return ReturnInfo.error("班课已结束");
        }
        if (course.getStatus() == 0){
            return ReturnInfo.error("该班课不允许加入");
        }
        int res = this.courseMapper.verifyIn(courseCode, userId);

        if (res > 0) {
            return ReturnInfo.error("已加入该班课");
        }
        this.courseMapper.participateInCourse(courseCode, userId, LocalDateTime.now());
        return ReturnInfo.success("加入成功");
    }

    @Override
    public ReturnInfo getCourseMember(String courseCode) {
        List<CourseMemberInfo> res = this.courseMapper.getCourseMember(courseCode);
        List<CourseMemberInfoWithRank> ans = new ArrayList<>();
        int rank = 1;
        for (CourseMemberInfo c : res){
            CourseMemberInfoWithRank cr = new CourseMemberInfoWithRank();
            cr.setRealname(c.getRealname());
            cr.setScore(c.getScore());
            cr.setStudentId(c.getStudentId());
            if (ans.isEmpty()){
                rank = 1;
            } else {
                rank = c.getScore() == ans.get(ans.size()-1).getScore() ? rank : rank+1;
            }
            cr.setRank(rank);
            ans.add(cr);
        }
        return ReturnInfo.success("班课成员信息获取成功", ans);
    }

    @Override
    public ReturnInfo getUCourseByCode(Integer userId, String courseCode) {
        Course course = this.courseMapper.getUCourseByCode(userId, courseCode);
        return ReturnInfo.success("搜索成功", course);
    }

    @Override
    public ReturnInfo getCourseByCode(String courseCode) {
        /**
         * 判断班课是否存在
         */
        int isExist = this.courseMapper.verifyCourseCode(courseCode);
        if(isExist == 0){
            return ReturnInfo.error("班课不存在");
        }
        Course course = this.courseMapper.selectOne(new QueryWrapper<Course>().eq("course_code", courseCode));
//        if(course.getStatus() == 1){
//            return ReturnInfo.error("该班课不允许加入");
//        }else if(course.getIsEnd() == 1){
//            return ReturnInfo.error("该班课已结束");
//        }
        return ReturnInfo.success("班课获取成功",course);
    }

    @Override
    public ReturnInfo getUCreateCourse(Integer userId) {
        List course = this.courseMapper.selectList(new QueryWrapper<Course>().eq("creator",userId));
        return ReturnInfo.success("查询成功", course);
    }

    @Override
    public ReturnInfo getRankAndScore(Integer userId, String courseCode) {
        List<ParticipateInCourse> participateInCourses = participateInCourseMapper.selectList(
                new QueryWrapper<ParticipateInCourse>().eq("course_code", courseCode));
        int rank = 1;
        int score = 0;
        for(int index = 0; index < participateInCourses.size(); ++index){
            ParticipateInCourse pc = participateInCourses.get(index);
            if (index == 0){
                continue;
            }else {
                rank = pc.getScore() == participateInCourses.get(index-1).getScore() ? rank : rank+1;
            }
            if (pc.getUser_id() == userId){
                score = participateInCourses.get(index).getScore();
                break;
            }
        }
        Map<String, Integer> res = new HashMap<>();
        res.put("rank", rank);
        res.put("score", score);
        return ReturnInfo.success("获取成功", res);
    }

    @Override
    public ReturnInfo setJoinStatus(String course_code, Integer status) {
        Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("course_code", course_code));
        course.setStatus(status);
        courseMapper.update(course, new UpdateWrapper<Course>().set("status", status));
        return ReturnInfo.success("更新成功");
    }

    @Override
    public ReturnInfo isEnd(String course_code) {
        Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("course_code", course_code));
        return ReturnInfo.success("查询成功", course.getIsEnd());
    }

    @Override
    public ReturnInfo quit(String course_code, String student_id) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("student_id", student_id));
        int delete = participateInCourseMapper.delete(new QueryWrapper<ParticipateInCourse>()
                .eq("course_code", course_code)
                .eq("user_id", user.getId())
        );
        if (delete == 0){
            return ReturnInfo.error("学生退出班课失败");
        }
        return ReturnInfo.success("学生退出班课成功");
    }
}
