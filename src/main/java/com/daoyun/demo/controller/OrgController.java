package com.daoyun.demo.controller;

import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.service.IDictionaryDetailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 获得Org
 * @author: MaYan
 */

@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired(required = false)
    private IDictionaryDetailService iDictionaryDetailService;


    @ApiOperation("获取学校名")
    @GetMapping("/school")
    public ReturnInfo getSchool() {
        try {
            return this.iDictionaryDetailService.getSchool();
        } catch (Exception e) {
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("迭代获取下一层得组织")
    @GetMapping("next/{parentId}")
    public ReturnInfo getCollege(@PathVariable("parentId") Integer parentId) {
        try {
            return this.iDictionaryDetailService.getCollege(parentId);
        } catch (Exception e) {
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }


}
