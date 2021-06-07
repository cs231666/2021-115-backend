package com.daoyun.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.SysParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-10
 */
public interface ISysParamService extends IService<SysParam> {

    ReturnInfo getSysParam();

    ReturnInfo addSysParam(String paramName, String paramValue);

    ReturnInfo deleteSysParam(Integer paramId);

    ReturnInfo updateSysParam(int paramId, String paramName, String paramValue);
}
