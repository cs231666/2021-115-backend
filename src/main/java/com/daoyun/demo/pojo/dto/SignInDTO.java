package com.daoyun.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {

    private String courseCode;
    private Double longitude; //经度
    private Double latitude; //纬度
    private String gesture;  //签到的手势，空表示为普通的坐标签到
    private Integer minutes; //签到持续的时间
    private Integer second;
}
