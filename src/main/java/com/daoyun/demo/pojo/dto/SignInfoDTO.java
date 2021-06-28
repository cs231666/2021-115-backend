package com.daoyun.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInfoDTO {
    private String realname;
    private String studentId;
    private Date signTime;
    private Double distance;
}
