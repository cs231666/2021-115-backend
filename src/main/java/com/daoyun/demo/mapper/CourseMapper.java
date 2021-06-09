package com.daoyun.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daoyun.demo.pojo.Course;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.dto.CourseMemberInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MaYan
 * @since 2021-05-31
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 当前用户所加入的班课，多表联查
     * @param userId
     * @return
     */
    @Select("SELECT c.* from course c,participate_in_course pic " +
            "where " +
            "c.course_code=pic.course_code " +
            "and " +
            "user_id= #{userId}")
    List<Course> getUCourse(Integer userId);

    /**
     * 加入班课
     * @param courseCode
     * @param userId
     * @param creationDate 加入时间
     */
    @Insert("insert into participate_in_course(course_code,user_id,creation_date,creator) values(#{courseCode}, #{userId}, #{creationDate},#{userId})")
    void participateInCourse(String courseCode, Integer userId,LocalDateTime creationDate);

    @Select("SELECT u.realname, u.student_id, pic.score FROM user u, participate_in_course pic WHERE pic.course_code = #{courseCode} AND u.id = pic.user_id")
    List<CourseMemberInfo> getCourseMember(String courseCode);

    @Select("SELECT c.* FROM course c,participate_in_course pic WHERE c.course_code = pic.course_code and pic.user_id = #{userId} and pic.course_code = #{courseCode}")
    Course getUCourseByCode(Integer userId, String courseCode);
}
