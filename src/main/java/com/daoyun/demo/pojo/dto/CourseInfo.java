package com.daoyun.demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 描述班课状态信息
 * @author: MaYan
 */

@Data
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfo {
    private String courseCode;
    private Integer status;
    private Integer isEnd;
}
