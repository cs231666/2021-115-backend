package com.daoyun.demo.service;

import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.SysMenuVo;

public interface ISysMenuService {
    ReturnInfo get(String id);

    ReturnInfo save(SysMenuVo sysMenuVo);

    ReturnInfo delete(String id);
}
