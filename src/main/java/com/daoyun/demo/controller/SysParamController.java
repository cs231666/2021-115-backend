package com.daoyun.demo.controller;


import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.service.ISysParamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author MaYan
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/sys-param")
public class SysParamController {
    @Autowired
    private ISysParamService iSysParamService;

    @ApiOperation("添加系统参数")
    @PostMapping({"{paramName}/{paramValue}"})
    public ReturnInfo addSysParam(@PathVariable("paramName") String paramName, @PathVariable("paramValue") String paramValue) {
        try {
            return this.iSysParamService.addSysParam(paramName, paramValue);
        }catch(Exception e){
            return ReturnInfo.error("添加失败");
        }
    }
    @ApiOperation("实现删除系统参数")
    @DeleteMapping({"{paramId}"})
    public ReturnInfo deleteSysParam(@PathVariable("paramId") Integer paramId) {
        try {
            return this.iSysParamService.deleteSysParam(paramId);
        }catch (Exception e){
            return ReturnInfo.error("删除失败");
        }
    }

    @ApiOperation("实现更新系统参数")
    @PutMapping({"{paramId}/{paraName}/{paramValue}"})
    public ReturnInfo updateSysParam(@PathVariable("paramId") int paramId, @PathVariable("paraName") String paramName, @PathVariable("paramValue") String paramValue) {
        return this.iSysParamService.updateSysParam(paramId, paramName, paramValue);
    }


    @ApiOperation("实现获取系统参数")
    @GetMapping({""})
    public ReturnInfo getSysParam() {
        try {
            return this.iSysParamService.getSysParam();
        }catch(Exception e){
            return ReturnInfo.error("查询失败");
        }
    }

}
