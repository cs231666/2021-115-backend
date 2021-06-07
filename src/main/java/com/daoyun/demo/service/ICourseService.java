package com.daoyun.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daoyun.demo.pojo.Course;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.CourseDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-31
 */
public interface ICourseService extends IService<Course> {

    ReturnInfo createCourse(CourseDto courseDto);

    ReturnInfo deleteCourseById(Integer courseId);

    ReturnInfo updateCourseById(Integer courseId,String courseName,String note);

    ReturnInfo getCourse();

    ReturnInfo getUCourse(Integer userId);

    ReturnInfo participateInCourse(Integer courseId, Integer userId);
}
