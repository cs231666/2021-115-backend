package com.daoyun.demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SignVo {

    private Integer id;
    private String courseCode;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime endTime;
    private Double longitude;
    private Double latitude;
    private String gesture;
    private Integer signCount;
    private Integer courseNumber;
}
