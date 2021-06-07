package com.daoyun.demo.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daoyun.demo.pojo.DictionaryDetail;
import com.daoyun.demo.pojo.Login;
import com.daoyun.demo.pojo.User;
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

    @Delete("DELETE FROM login where username = #{username}")
    void deleteLogin(String username);
}
