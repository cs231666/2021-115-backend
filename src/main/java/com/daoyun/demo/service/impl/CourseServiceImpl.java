package com.daoyun.demo.service.impl;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daoyun.demo.mapper.CourseMapper;
import com.daoyun.demo.pojo.Course;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.dto.CourseDto;
import com.daoyun.demo.pojo.dto.CourseInfo;
import com.daoyun.demo.pojo.dto.CourseMemberInfo;
import com.daoyun.demo.service.ICourseService;
import com.daoyun.demo.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
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
    @Autowired
    private CourseMapper courseMapper;

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
        Course course = new Course();
        course.setCourseId(courseInfo.getCourseId());
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
        return ReturnInfo.success("班课成员信息获取成功", res);
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
        if(course.getStatus() == 1){
            return ReturnInfo.error("该班课不允许加入");
        }else if(course.getIsEnd() == 1){
            return ReturnInfo.error("该班课已结束");
        }
        return ReturnInfo.success("班课获取成功",course);
    }

    @Override
    public ReturnInfo getUCreateCourse(Integer userId) {
        List course = this.courseMapper.selectList(new QueryWrapper<Course>().eq("creator",userId));
        return ReturnInfo.success("查询成功", course);
    }
}
