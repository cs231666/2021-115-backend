package com.daoyun.demo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_param")
@ApiModel(value="SysParam对象", description="")
public class SysParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "param_id", type = IdType.AUTO)
    private Integer paramId;

    @TableField("param_key")
    private String paramKey;

    @TableField("param_name")
    private String paramName;

    @TableField("param_value")
    private String paramValue;
}
