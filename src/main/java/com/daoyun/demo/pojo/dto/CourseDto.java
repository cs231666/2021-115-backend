package com.daoyun.demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @description: 班课Dto
 * @author: MaYan
 */
@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {


    @TableField("course_name")
    private String courseName;//课程名

    private String org;//学校组织名称

    private String teacher;

    private String className;//备注

    private String term;//学期

    private Integer creator;

}
