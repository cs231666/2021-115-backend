package com.daoyun.demo.pojo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseMemberInfoWithRank {
    int rank;
    String realname;
    String studentId;
    Integer score;
}
