package com.daoyun.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.daoyun.demo.pojo.DictionaryDetail;
import com.daoyun.demo.pojo.ReturnInfo;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-11
 */
public interface IDictionaryDetailService extends IService<DictionaryDetail> {

    ReturnInfo getDictDetailByPage(String dictCode,Integer page, Integer limit, Integer style);

    ReturnInfo getDictDetailByDictName(String name);

    ReturnInfo addDictDetail(String dictCode, String code, String name);

    ReturnInfo deleteDictDetail(Integer detailId);

    ReturnInfo updateDictDetail(Integer detailId, String name);

    ReturnInfo moveUp(Integer dictId);

    ReturnInfo moveDown(Integer dictId);

    ReturnInfo getSchool();

    ReturnInfo getCollege(Integer parentId);

    ReturnInfo getTerm();
}
