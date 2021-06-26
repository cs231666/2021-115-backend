package com.daoyun.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignLogDTO {
    private String course_code;
    private Integer student_id;
    private Double longitude;
    private Double latitude;
    private String gesture;
}
