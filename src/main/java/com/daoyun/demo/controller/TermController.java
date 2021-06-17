package com.daoyun.demo.controller;

import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.service.IDictionaryDetailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 从数据字典里获取学期
 * @author: MaYan
 */

@RestController
@RequestMapping("/term")
public class TermController {

    @Autowired(required = false)
    private IDictionaryDetailService iDictionaryDetailService;

    @ApiOperation("获取学期")
    @GetMapping("/")
    public ReturnInfo getTerm() {
        try {
            return this.iDictionaryDetailService.getTerm();
        } catch (Exception e) {
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }
}
