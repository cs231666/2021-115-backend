package com.daoyun.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.daoyun.demo.pojo.Dictionary;
import com.daoyun.demo.pojo.ReturnInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-10
 */
public interface IDictionaryService extends IService<Dictionary> {
    /**
     * 添加字典
     * @param dictCode
     * @param dictName
     * @param description
     * @return
     */
    ReturnInfo addDict(String dictCode, String dictName, String description);

    /**
     * 获取所有字典（分页）
     * @param page
     * @param limit
     * @return
     */
    ReturnInfo getDictByPage(Integer page, Integer limit, Integer style);

    /**
     * 删除字典
     * @param dictId
     * @return
     */
    ReturnInfo deleteDict(Integer dictId);

    /**
     * 更新字典
     * @param dictId
     * @param dictCode
     * @param dictName
     * @param description
     * @return
     */
    ReturnInfo updateDict(Integer dictId, String dictCode, String dictName, String description);
}
