package com.daoyun.demo.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daoyun.demo.pojo.Dictionary;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.service.IDictionaryService;
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
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private IDictionaryService iDictionaryService;

    @ApiOperation("实现增加字典数据")
    @PostMapping({"/{dictCode}/{dictName}/{description}"})
    public ReturnInfo addDict(@PathVariable("dictCode") String dictCode, @PathVariable("dictName") String dictName, @PathVariable("description") String description) {
        try {
            return this.iDictionaryService.addDict(dictCode, dictName, description);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("实现删除字典数据")
    @DeleteMapping({"/{dictId}"})
    public ReturnInfo deleteDict(@PathVariable("dictId") Integer dictId) {
        try {
            return this.iDictionaryService.deleteDict(dictId);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }


    @ApiOperation("实现更新字典数据")
    @PutMapping({"/{dictId}/{dictCode}/{dictName}/{description}"})
    public ReturnInfo updateDict(@PathVariable("dictId") Integer dictId, @PathVariable("dictCode") String dictCode,
                                 @PathVariable("dictName") String dictName, @PathVariable("description") String description) {
        try {
            return this.iDictionaryService.updateDict(dictId, dictCode, dictName, description);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }


//    @ApiOperation("实现获取字典数据")
//    @GetMapping({"{limit}/{page}"})
//    public ReturnInfo getDict(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit) {
//
//        return this.iDictionaryService.getDictByPage(page, limit);
//    }



    @ApiOperation("实现获取字典数据（分页）")
    @GetMapping({"/{limit}/{page}/{style}"})
    public ReturnInfo getDict(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit, @PathVariable("style") Integer style) {
        /**
         * style == 1 ,升序
         * 否则，降序
         */
        return this.iDictionaryService.getDictByPage(page, limit, style);
    }
}
