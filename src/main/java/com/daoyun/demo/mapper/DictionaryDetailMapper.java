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

    @Select("SELECT * from dictionary_detail WHERE ( seq < #{currentSeq}  OR seq=(SELECT MIN(seq) FROM dictionary_detail ) ) AND dict_code = #{dictCode} ORDER BY seq DESC limit 1")
    DictionaryDetail previous(String dictCode, Integer currentSeq);

    @Select("SELECT * from dictionary_detail WHERE ( seq > #{currentSeq}  OR seq=(SELECT MAX(seq) FROM dictionary_detail ) ) AND dict_code = #{dictCode} ORDER BY seq ASC  limit 1")
    DictionaryDetail after(String dictCode, Integer currentSeq);

    @Select("SELECT MAX(seq) from dictionary_detail where dict_code = #{dictCode}")
    Integer selectMax(String dictCode);


//    @Select("SELECT name from dictionary_detail where dict_code = #{dictCode}")
//    List getSchool(String dictCode);
}
