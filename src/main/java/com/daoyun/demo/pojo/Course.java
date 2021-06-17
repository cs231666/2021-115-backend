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

    private String org;//学校组织

    private Integer status;//是否允许加入，0允许，1不允许

    @TableField("is_end")
    private Integer isEnd;//是否允许结束，0未结束，1结束


    private String teacher;

    @TableField("class_name")
    private String className;//班级

    private String term;//学期

    @TableField("creation_date")
    private LocalDateTime creationDate;//创建时间

    private Integer creator;

    @TableField("modification_date")
    private LocalDateTime modificationDate;//修改时间

    private String modifier;
}
