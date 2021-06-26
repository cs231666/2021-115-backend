package com.daoyun.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daoyun.demo.pojo.SignIn;
import com.daoyun.demo.pojo.dto.SignInfoDTO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface SignInMapper extends BaseMapper<SignIn> {


    @Select("select id, course_id, status, create_time, end_time, longitude, latitude, gesture from sign_in where course_id = #{course_id} and end_time > #{cur}")
    SignIn getCurSignIn(int course_id, Date cur);

    @Select("select course_id from sign_in where course_id=#{course_id}")
    List<Integer> getCourseId(int course_id);

    @Select("select * from sign_in where course_code = #{course_code}")
    List<SignIn> getAllByCourseCode(String course_code);

    @Select("select u.realname, u.student_id, s.sign_time from user u left join sign_log s on u.student_id=s.student_id where sign_id=#{sign_id}")
    List<SignInfoDTO> getSignInfoById(Integer sign_id);
}
