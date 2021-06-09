package com.daoyun.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daoyun.demo.mapper.SysParamMapper;
import com.daoyun.demo.pojo.DictionaryDetail;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.SysParam;
import com.daoyun.demo.service.ISysParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-10
 */
@Service
public class SysParamServiceImpl extends ServiceImpl<SysParamMapper, SysParam> implements ISysParamService {

    @Autowired
    SysParamMapper sysParamMapper;



    @Override
    public ReturnInfo addSysParam(String paramKey, String paramName, String paramValue) {
        SysParam sysParam = new SysParam();
        sysParam.setParamKey(paramKey);
        sysParam.setParamName(paramName);
        sysParam.setParamValue(paramValue);
        this.sysParamMapper.insert(sysParam);
        return ReturnInfo.success("添加成功");
    }

    @Override
    public ReturnInfo deleteSysParam(Integer paramId) {
        this.sysParamMapper.deleteById(paramId);
        return ReturnInfo.success("删除成功");
    }
    @Override
    public ReturnInfo updateSysParam(int paramId, String paramKey, String paramName, String paramValue) {
        SysParam sysParam = new SysParam();
        sysParam.setParamId(paramId);
        sysParam.setParamKey(paramKey);
        sysParam.setParamName(paramName);
        sysParam.setParamValue(paramValue);
        this.sysParamMapper.updateById(sysParam);
        return ReturnInfo.success("更新成功");
    }

    @Override
    public ReturnInfo getSysParamKey(String paramKey) {
        int res = this.sysParamMapper.selectCount(new QueryWrapper<SysParam>().eq("param_key", paramKey));
        if(res>0){
            return ReturnInfo.error("关键字重复");
        }else {
            return ReturnInfo.success("");
        }
    }

    @Override
    public ReturnInfo getSysParam() {
        List sysParams = this.sysParamMapper.selectList(new QueryWrapper<>());
        return ReturnInfo.success("查询成功", sysParams);
    }
}
