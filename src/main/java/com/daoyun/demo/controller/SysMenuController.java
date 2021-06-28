package com.daoyun.demo.controller;

import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.dto.SysMenuVo;
import com.daoyun.demo.service.ISysMenuService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/sysMenu/")
public class SysMenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @GetMapping("get/{id}")
    @SneakyThrows
    public ReturnInfo get(@PathVariable("id") String id){
        return sysMenuService.get(id);
    }

    @PostMapping("/save")
    @SneakyThrows
    public ReturnInfo save(@RequestBody SysMenuVo sysMenuVo){
        return sysMenuService.save(sysMenuVo);
    }

    @GetMapping("delete/{id}")
    @SneakyThrows
    public ReturnInfo delete(@PathVariable("id") String id){
        return sysMenuService.delete(id);
    }
}
