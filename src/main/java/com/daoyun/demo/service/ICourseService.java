package com.daoyun.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daoyun.demo.pojo.Course;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.CourseDto;
import com.daoyun.demo.pojo.dto.CourseInfo;

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

    ReturnInfo updateCourseById(CourseInfo courseInfo);

    ReturnInfo getCourse();

    ReturnInfo getUCourse(Integer userId);

    ReturnInfo participateInCourse(String courseCode, Integer userId);

    ReturnInfo getCourseMember(String courerCode);

    ReturnInfo getUCourseByCode(Integer userId, String courseCode);

    ReturnInfo getCourseByCode(String courseCode);

    ReturnInfo getUCreateCourse(Integer userId);

    ReturnInfo getRankAndScore(Integer userId, String courseCode);

    ReturnInfo setJoinStatus(String course_code, Integer status);

    ReturnInfo isEnd(String course_code);

}
