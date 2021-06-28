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
            e.printStackTrace();
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


    /**
     * 20210608 21：08
     * 修改成按照seq排序
     * @param dictCode
     * @param page
     * @param limit
     * @param style
     * @return
     */
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

    @ApiOperation("上移字典明细")
    @PutMapping({"up/{dictId}"})
    public ReturnInfo moveUp(@PathVariable("dictId") Integer dictDetailId) {
        try {
            return this.iDictionaryDetailService.moveUp(dictDetailId);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("下移字典明细")
    @PutMapping({"down/{dictId}"})
    public ReturnInfo moveDown(@PathVariable("dictId") Integer dictDetailId) {
        try {
            return this.iDictionaryDetailService.moveDown(dictDetailId);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("通过字典名获取字典明细(前端)")
    @GetMapping({"{dictname}"})
    public ReturnInfo getDictDetailByDictName(@PathVariable("dictname") String dictname) {
        try {
            return this.iDictionaryDetailService.getDictDetailByDictName(dictname);
        }catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("添加学校")
    @PostMapping("add/{schoolName}")
    public ReturnInfo addSchool(@PathVariable("schoolName") String schoolName){
        try {
            return this.iDictionaryDetailService.addSchool(schoolName);
        } catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }
    @ApiOperation("添加院系")
    @PostMapping("add/{schoolName}/{college}")
    public ReturnInfo addSchool(@PathVariable("schoolName") String schoolName, @PathVariable("college") String college){
        try {
            return this.iDictionaryDetailService.addCollege(schoolName, college);
        } catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("修改学校名称")
    @PostMapping("updateSchool/{id}/{newSchoolName}")
    public ReturnInfo updateSchool(@PathVariable("id") Integer id, @PathVariable("newSchoolName") String name){
        try {
            return this.iDictionaryDetailService.updateSchool(id, name);
        } catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }
    @ApiOperation("修改院系名称")
    @PutMapping("updateCollege/{id}/{newCollegeName}")
    public ReturnInfo updateCollege(@PathVariable("id") Integer id, @PathVariable("newCollegeName") String name){
        try {
            return this.iDictionaryDetailService.updateCollege(id, name);
        } catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("删除学校")
    @PostMapping("deleteSchool/{id}")
    public ReturnInfo deleteSchool(@PathVariable("id") Integer id){
        try {
            return this.iDictionaryDetailService.deleteSchool(id);
        } catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

    @ApiOperation("删除院系")
    @PostMapping("deleteCollege/{id}")
    public ReturnInfo deleteCollege(@PathVariable("id") Integer id){
        try {
            return this.iDictionaryDetailService.deleteCollege(id);
        } catch (Exception e){
            return ReturnInfo.error("服务器内部错误，无法完成请求");
        }
    }

}
