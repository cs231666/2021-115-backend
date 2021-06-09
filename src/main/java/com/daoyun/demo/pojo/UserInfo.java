package com.daoyun.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @description: 用户信息的展示，忽略一些隐私信息
 * @author: MaYan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String sex;

    private String nickname;

    private String realname;

    private String phone;

    private String email;

//    @TableField("org_id")
//    private Integer orgId;

    private LocalDateTime birthday;

}
