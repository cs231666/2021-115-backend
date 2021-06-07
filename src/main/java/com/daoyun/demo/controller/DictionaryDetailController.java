package com.daoyun.demo.controller;


import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.service.IDictionaryDetailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author MaYan
 * @since 2021-05-11
 */
@RestController
@RequestMapping("/dictionary-detail")
public class DictionaryDetailController {
    @Autowired(required = false)
    private IDictionaryDetailService iDictionaryDetailService;


    @ApiOperation("实现添加字典明细")
    @PostMapping({"/{dictCode}/{code}/{name}"})
    public ReturnInfo addDictDetail(@PathVariable("dictCode") String dictCode, @PathVariable("code") String code, @PathVariable("name") String name) {
        try {
            return this.iDictionaryDetailService.addDictDetail(dictCode, code, name);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }
    @ApiOperation("实现删除字典明细")
    @DeleteMapping({"{detailId}"})
    public ReturnInfo deleteDictDetail(@PathVariable("detailId") Integer detailId) {
        try {
            return this.iDictionaryDetailService.deleteDictDetail(detailId);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("实现更新字典明细")
    @PutMapping({"{detailId}/{name}"})
    public ReturnInfo updateDictDetail(@PathVariable("detailId") Integer detailId, @PathVariable("name") String name) {
        try {
            return this.iDictionaryDetailService.updateDictDetail(detailId, name);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("实现获取字典明细")
    @GetMapping({"{dictCode}/{page}/{limit}/{style}"})
    public ReturnInfo getDictDetail(@PathVariable("dictCode") String dictCode,@PathVariable("page") Integer page, @PathVariable("limit") Integer limit,
                                    @PathVariable("style") Integer style) {
        try {
            return this.iDictionaryDetailService.getDictDetailByPage(dictCode, page, limit, style);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }
    @ApiOperation("通过字典名获取字典明细")
    @GetMapping({"{dictname}"})
    public ReturnInfo getDictDetailByDictName(@PathVariable("dictname") String dictname) {
        try {
            return this.iDictionaryDetailService.getDictDetailByDictName(dictname);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }
}
