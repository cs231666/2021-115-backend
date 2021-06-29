package com.daoyun.demo.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipateInCourse {

    private Integer id;
    private String courseCode;
    private Integer userId;
    private Integer score;
    private Date creationDate;
    private Integer creator;
    private Date modificationDate;
    private String modifier;
}
