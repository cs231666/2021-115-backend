package com.daoyun.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daoyun.demo.pojo.Course;
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
            "c.course_id=pic.course_id " +
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
    void participateInCourse(Integer courseCode, Integer userId,LocalDateTime creationDate);
}
