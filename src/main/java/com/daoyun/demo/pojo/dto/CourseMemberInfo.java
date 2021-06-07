package com.daoyun.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 班课成员信息
 * @author: MaYan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseMemberInfo {
    String realname;
    String studentId;
    Integer score;
}
