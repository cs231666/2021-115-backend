package com.daoyun.demo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daoyun.demo.mapper.DictionaryDetailMapper;
import com.daoyun.demo.pojo.Dictionary;
import com.daoyun.demo.pojo.DictionaryDetail;
import com.daoyun.demo.pojo.ReturnInfo;
import com.daoyun.demo.pojo.User;
import com.daoyun.demo.service.IDictionaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author MaYan
 * @since 2021-05-11
 */
@Service
public class DictionaryDetailServiceImpl extends ServiceImpl<DictionaryDetailMapper, DictionaryDetail> implements IDictionaryDetailService {

    @Autowired
    private DictionaryDetailMapper dictionaryDetailMapper;


    /**
     * 添加明细
     *
     * @param dictCode
     * @param code
     * @param name
     * @return
     */
    @Override
    public ReturnInfo addDictDetail(String dictCode, String code, String name) {

        if (this.dictionaryDetailMapper.selectOne(new QueryWrapper<DictionaryDetail>().eq("dict_code", dictCode).eq("name", name)) != null) {
            return ReturnInfo.created("该字典下此名称已经存在，请修改以后再提交！");
        }
        DictionaryDetail dictionaryDetail = new DictionaryDetail();
        dictionaryDetail.setDictCode(dictCode);
        dictionaryDetail.setCode(code);
        dictionaryDetail.setName(name);
        this.dictionaryDetailMapper.insert(dictionaryDetail);
        return ReturnInfo.success("添加成功");
    }

    /**
     * 删除明细
     *
     * @param detailId
     * @return
     */
    @Override
    public ReturnInfo deleteDictDetail(Integer detailId) {
        this.dictionaryDetailMapper.deleteById(detailId);
        return ReturnInfo.success("删除成功");
    }

    /**
     * 更新明细
     *
     * @param detailId
     * @param name
     * @return
     */
    @Override
    public ReturnInfo updateDictDetail(Integer detailId, String name) {
        DictionaryDetail dictionaryDetail = new DictionaryDetail();
        dictionaryDetail.setDetailId(detailId);
        dictionaryDetail.setName(name);
        this.dictionaryDetailMapper.updateById(dictionaryDetail);
        return ReturnInfo.success("更新成功");
    }

    /**
     * 分页查询数据字典明细
     *
     * @param dictCode
     * @param page
     * @param limit
     * @param style
     * @return
     */
    @Override
    public ReturnInfo getDictDetailByPage(String dictCode, Integer page, Integer limit, Integer style) {
        /**
         * page 当前页
         * limit 每页数量
         */
        Page<DictionaryDetail> dictionaryDetailPage = new Page<>(page, limit);
        if (style == 1) {
            dictionaryDetailPage.addOrder(OrderItem.asc("detail_id"));

        } else {
            dictionaryDetailPage.addOrder(OrderItem.desc("detail_id"));
        }
        IPage<DictionaryDetail> dictionaryDetailIPage = dictionaryDetailMapper.getDictDetailByPage(dictionaryDetailPage, dictCode);
        return ReturnInfo.success("查询成功", dictionaryDetailIPage.getRecords());
    }

    @Override
    public ReturnInfo getDictDetailByDictName(String dictname) {
//        List<DictionaryDetail>dictionaryDetails = this.dictionaryDetailMapper.selectList(new QueryWrapper<DictionaryDetail>().eq("name",name));
        List<DictionaryDetail> dictionaryDetails = this.dictionaryDetailMapper.getDictDetailByDictName(dictname);
        return ReturnInfo.success("查询成功",dictionaryDetails);
    }
}
