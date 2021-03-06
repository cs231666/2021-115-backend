package com.daoyun.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.daoyun.demo.pojo.SignIn;
import com.daoyun.demo.pojo.SignLog;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SignLogMapper extends BaseMapper<SignLog> {

    @Select("select count(*) from sign_log where sign_id=#{signId}")
    int getSignCountBySignId(Integer signId);


}
