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
    private String course_code;
    private Integer user_id;
    private Integer score;
    private Date creation_date;
    private Integer creator;
    private Date modification_date;
    private String modifier;
}
