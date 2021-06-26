package com.daoyun.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sign_in")
public class SignIn {
//    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String courseCode;
    private int status;

    @TableField(value = "create_time")
    private LocalDateTime createTime;
    @TableField(value = "end_time")
    private LocalDateTime endTime;
    private Double longitude;
    private Double latitude;
    private String gesture;
}
