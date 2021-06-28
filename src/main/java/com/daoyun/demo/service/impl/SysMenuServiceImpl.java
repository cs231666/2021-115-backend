package com.daoyun.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.daoyun.demo.mapper.SysMenuMapper;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.SysMenu;
import com.daoyun.demo.pojo.dto.SysMenuVo;
import com.daoyun.demo.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired(required = false)
    private SysMenuMapper sysMenuMapper;
    @Override
    public ReturnInfo get(String id) {
        SysMenu sysMenu = sysMenuMapper.selectOne(new QueryWrapper<SysMenu>().eq("menu_id", id));
        return ReturnInfo.success("获取菜单成功", sysMenu);
    }

    @Override
    public ReturnInfo save(SysMenuVo sysMenuVo) {
        SysMenu sysMenu = new SysMenu();
        BeanUtil.copyProperties(sysMenuVo, sysMenu);
        sysMenuMapper.insert(sysMenu);
        return ReturnInfo.success("创建菜单成功");
    }

    @Override
    public ReturnInfo delete(String id) {
        sysMenuMapper.delete(new QueryWrapper<SysMenu>().eq("menu_id", id));
        return ReturnInfo.success("删除菜单成功");
    }
}
