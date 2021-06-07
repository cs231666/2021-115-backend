package com.daoyun.demo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daoyun.demo.pojo.Dictionary;
import com.daoyun.demo.pojo.DictionaryDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author MaYan
 * @since 2021-05-11
 */
public interface DictionaryDetailMapper extends BaseMapper<DictionaryDetail> {

    @Select("SELECT * FROM dictionary_detail where dict_code = #{dictCode}")
    IPage<DictionaryDetail> getDictDetailByPage(Page<DictionaryDetail> dictionaryDetailPage, String dictCode);

    @Select("SELECT dd.* FROM dictionary_detail dd,dictionary d where d.dict_name = #{dictName} AND d.dict_code = dd.dict_code")
    List<DictionaryDetail> getDictDetailByDictName(String dictName);
}
