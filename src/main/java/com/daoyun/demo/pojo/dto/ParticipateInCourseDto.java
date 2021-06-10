package com.daoyun.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 用来通过二维码和用户id加入班课
 * @author: MaYan
 */
@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class ParticipateInCourseDto {
    String courseCode;
    String qrCode;
    Integer userId;
}
