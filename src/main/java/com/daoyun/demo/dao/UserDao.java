package com.daoyun.demo.dao;

import com.daoyun.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("select * from user")
    List<User> getUsers();

    @Select("select count(*) from user where username=#{username}")
    int getUserCount(String username);


    @Select("select * from user where username=#{username}")
    User getUserByUsername(String username);


    @Update("update user set token=#{token} where username=#{username}")
    int updateUserToken(String username,String token);

    @Insert("insert into user(username,password) values(#{username}, #{password})")
    void AddUser(String username, String password);
}
