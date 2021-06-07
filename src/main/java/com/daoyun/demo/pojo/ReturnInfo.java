package com.daoyun.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 公共返回对象
 * @author: MaYan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnInfo {

    private Integer code;
    private String msg;
    private Object obj;

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static  ReturnInfo success(String message){
        return new ReturnInfo(200, message, null);
    }

    /**
     * 成功返回结果
     * @param message
     * @param obj
     * @return
     */
    public static  ReturnInfo success(String message, Object obj){
        return new ReturnInfo(200, message, obj);
    }

    /**
     * 资源已存在
     * @param message
     * @return
     */
    public static ReturnInfo created(String message){
        return  new ReturnInfo(201,message,null);
    }
    /**
     * 错误的要求，参数没有通过有效性验证。
     * @param message
     * @return
     */
    public static ReturnInfo badrequest(String message){
        return new ReturnInfo(400,message,null);
    }

    /**
     * 如果用户已登录，则返回（禁止）。
     * @param message
     * @return
     */
    public static ReturnInfo forbidden(String message){
        return new ReturnInfo(403,message,null);
    }

    public static ReturnInfo notfound(String message){
        return new ReturnInfo(404,message,null);
    }

    /**
     * 其他异常,失败返回结果
     * @param message
     * @return
     */
    public static ReturnInfo error(String message){
        return new ReturnInfo(500, message, null);
    }

    /**
     * 其他异常,失败返回结果
     * @param message
     * @param obj
     * @return
     */
    public static ReturnInfo error(String message,Object obj){
        return new ReturnInfo(500, message, obj);
    }
}
