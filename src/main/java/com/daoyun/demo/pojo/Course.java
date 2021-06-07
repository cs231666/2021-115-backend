package com.daoyun.demo.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author MaYan
 * @since 2021-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Course对象", description="")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    @TableField("course_code")
    private String courseCode;//课程号

    @TableField("course_name")
    private String courseName;//课程名

    private String teacher;

    @TableField("qr_code")
    private String qrCode;//二维码

    private String note;//备注

    private String term;//学期

    @TableField("creation_date")
    private LocalDateTime creationDate;//创建时间

    private String creator;

    @TableField("modification_date")
    private LocalDateTime modificationDate;//修改时间

    private String modifier;


}
