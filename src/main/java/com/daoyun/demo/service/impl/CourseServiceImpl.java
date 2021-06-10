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
         * 根据班课号生成二维码
         */
        String qrCode = QRCodeUtil.getBase64QRCode(code);


        /**
         * 存到数据库
         */
        course.setCourseCode(code);
        course.setCourseName(courseDto.getCourseName());
        course.setTeacher(courseDto.getTeacher());
        course.setQrCode(qrCode);
        course.setNote(courseDto.getNote());
        course.setTerm(courseDto.getTerm());
        course.setCreationDate(LocalDateTime.now());
        course.setCreator(courseDto.getTeacher());
        this.courseMapper.insert(course);
        return ReturnInfo.success("创建成功");
    }

    @Override
    public ReturnInfo deleteCourseById(Integer courseId) {
        this.courseMapper.deleteById(courseId);
        return ReturnInfo.success("删除成功");
    }

    @Override
    public ReturnInfo updateCourseById(Integer courseId, String courseName, String note) {
        Course course = new Course();
        course.setCourseId(courseId);
        course.setCourseName(courseName);
        course.setNote(note);
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
         * 此处需要添加判断班课是否过期的逻辑
         */

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
    public ReturnInfo getCourseByQrCode(String qrCode) {
        /**
         * 此处需要添加判断班课是否过期的逻辑
         */


        /**
         * 判断用户是否已加入该班课
         */
        Course course = this.courseMapper.selectOne(new QueryWrapper<Course>().eq("qr_code", qrCode));
        return ReturnInfo.success("班课获取成功",course);

    }

    @Override
    public ReturnInfo getCourseMember(String courerCode) {
        List<CourseMemberInfo> res = this.courseMapper.getCourseMember(courerCode);
        return ReturnInfo.success("班课成员信息获取成功", res);
    }

    @Override
    public ReturnInfo getUCourseByCode(Integer userId, String courseCode) {
        Course course = this.courseMapper.getUCourseByCode(userId, courseCode);
        return ReturnInfo.success("搜索成功", course);
    }

    @Override
    public ReturnInfo getCourseByCode(String courseCode) {
        Course course = this.courseMapper.selectOne(new QueryWrapper<Course>().eq("course_code", courseCode));
        return ReturnInfo.success("班课获取成功",course);
    }
}
