package com.daoyun.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daoyun.demo.pojo.Dictionary;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author MaYan
 * @since 2021-05-10
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {
    /**
     * 获取所有数据字典（分页）
     *
     * @param dictionaryPage
     * @return
     */

    IPage<Dictionary> getDictByPage(Page<Dictionary> dictionaryPage);


}
