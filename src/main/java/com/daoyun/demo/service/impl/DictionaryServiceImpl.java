package com.daoyun.demo.service.impl;


import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daoyun.demo.mapper.DictionaryMapper;
import com.daoyun.demo.pojo.Dictionary;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-10
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements IDictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    /**
     * 添加数据字典
     *
     * @param dictCode
     * @param dictName
     * @param description
     * @return
     */
    @Override
    public ReturnInfo addDict(String dictCode, String dictName, String description) {
        if (this.dictionaryMapper.selectOne(new QueryWrapper<Dictionary>().eq("dict_code", dictCode)) != null) {
            return ReturnInfo.created("字典类型和字典编码已经存在，请修改以后再提交！");
        }
        Dictionary dictionary = new Dictionary();
        dictionary.setDictCode(dictCode);
        dictionary.setDictName(dictName);
        dictionary.setDescription(description);
        this.dictionaryMapper.insert(dictionary);
        return ReturnInfo.success("添加成功");
    }

    /**
     * 根据dictId删除数据字典
     *
     * @param dictId
     * @return
     */
    @Override
    public ReturnInfo deleteDict(Integer dictId) {
        this.dictionaryMapper.deleteById(dictId);
        return ReturnInfo.success("删除成功");
    }

    /**
     * 更新字典
     *
     * @param dictId
     * @param dictCode
     * @param dictName
     * @param description
     * @return
     */
    @Override
    public ReturnInfo updateDict(Integer dictId, String dictCode, String dictName, String description) {
        Dictionary dictionary = new Dictionary();
        dictionary.setDictId(dictId);
        dictionary.setDictCode(dictCode);
        dictionary.setDictName(dictName);
        dictionary.setDescription(description);
        this.dictionaryMapper.updateById(dictionary);
        return ReturnInfo.success("更新成功");
    }

    /**
     * 获取数据字典（分页）
     *
     * @param page
     * @param limit
     * @param orderby
     * @param style
     * @return
     */
    @Override
    public ReturnInfo getDictByPage(Integer page, Integer limit, Integer style) {
        /**
         * page 当前页
         * limit 每页数量
         */

        Page<Dictionary> dictionaryPage = new Page<>(page, limit);
        if (style == 1) {
            dictionaryPage.addOrder(OrderItem.asc("dict_id"));

        } else {
            dictionaryPage.addOrder(OrderItem.desc("dict_id"));
        }
        IPage<Dictionary> dictionaryIPage = dictionaryMapper.getDictByPage(dictionaryPage);
        return ReturnInfo.success("查询成功", dictionaryIPage.getRecords());
    }


}
