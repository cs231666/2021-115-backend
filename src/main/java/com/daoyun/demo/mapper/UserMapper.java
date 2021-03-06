package com.daoyun.demo.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daoyun.demo.pojo.DictionaryDetail;
import com.daoyun.demo.pojo.Login;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.pojo.dto.SignInfoDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MaYan
 * @since 2021-05-06
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Insert("INSERT INTO login(username,password,login_time,token) VALUES(#{username},#{password},#{loginTime},#{token})")
    void insertInLogin(String username, String password, LocalDateTime loginTime, String token);

    @Insert("INSERT INTO login(username,login_time,token) VALUES(#{username},#{loginTime},#{token})")
    void insertInLoginByCode(String username, LocalDateTime loginTime, String token);

    @Insert("UPDATE login SET login_time = #{loginTime} Where username = #{username}")
    void updateInLoginByGitHub(String username, LocalDateTime loginTime);

    @Delete("DELETE FROM login where username = #{username}")
    void deleteLogin(String username);

    @Select("SELECT u.* FROM user u, login l WHERE l.token = #{token} and u.username = l.username")
    User getUserInfoByToken(String token);

    @Select("select u.realname, u.student_id, sl.sign_time, sl.distance from user u left join participate_in_course p on u.id=p.user_id left join sign_log sl on u.student_id=sl.student_id where p.course_code=#{course_code} " +
            "and sign_id=#{signIn_id}")
    List<SignInfoDTO> getUserSignInCourse(String course_code, Integer signIn_id);

    @Select("select u.realname, u.student_id from user u left join participate_in_course p on u.id=p.user_id where p.course_code=#{course_code} " +
            "and u.student_id not in (select student_id from sign_log where sign_id=#{signIn_id})")
    List<SignInfoDTO> getUserNotSignInCourse(String course_code, Integer signIn_id);
}
