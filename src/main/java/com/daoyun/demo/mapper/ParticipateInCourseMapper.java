package com.daoyun.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daoyun.demo.pojo.ParticipateInCourse;
import org.apache.ibatis.annotations.Select;

public interface ParticipateInCourseMapper extends BaseMapper<ParticipateInCourse> {

    @Select("SELECT COUNT(*) FROM `participate_in_course` WHERE course_code=#{course_code}")
    int getCourseNumber(String course_code);
}
