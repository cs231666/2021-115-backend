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
import java.util.Map;

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
        Integer maxSeq = this.dictionaryDetailMapper.selectMax(dictCode);

        DictionaryDetail dictionaryDetail = new DictionaryDetail();
        dictionaryDetail.setDictCode(dictCode);
        dictionaryDetail.setCode(code);
        dictionaryDetail.setName(name);
        dictionaryDetail.setSeq(maxSeq + 1);
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

    @Override
    public ReturnInfo moveUp(Integer dictId) {
        /**
         * 获取当前行的Seq
         */
        DictionaryDetail dDetail1 = this.dictionaryDetailMapper.selectById(dictId);
        Integer currentSeq = dDetail1.getSeq();

        /**
         * 获取该大类下 上一行的id,Seq
         */
        DictionaryDetail dDetail2 = this.dictionaryDetailMapper.previous(dDetail1.getDictCode(), currentSeq);
        Integer beforeId = dDetail2.getDetailId();
        Integer beforeSeq = dDetail2.getSeq();


        DictionaryDetail dictionaryDetail1 = new DictionaryDetail();
        dictionaryDetail1.setDetailId(beforeId);
        dictionaryDetail1.setSeq(currentSeq);
        this.dictionaryDetailMapper.updateById(dictionaryDetail1);

        DictionaryDetail dictionaryDetail2 = new DictionaryDetail();
        dictionaryDetail2.setDetailId(dictId);
        dictionaryDetail2.setSeq(beforeSeq);
        this.dictionaryDetailMapper.updateById(dictionaryDetail2);
        return ReturnInfo.success("移动成功");
    }

    @Override
    public ReturnInfo moveDown(Integer dictId) {
        /**
         * 获取当前行的Seq
         */
        DictionaryDetail dDetail1 = this.dictionaryDetailMapper.selectById(dictId);
        Integer currentSeq = dDetail1.getSeq();

        /**
         * 获取该大类下 上一行的id,Seq
         */
        DictionaryDetail dDetail2 = this.dictionaryDetailMapper.after(dDetail1.getDictCode(), currentSeq);
        Integer afterId = dDetail2.getDetailId();
        Integer afterSeq = dDetail2.getSeq();


        /**
         *将当前行的seq置为下一行的seq
         */
        DictionaryDetail dictionaryDetail2 = new DictionaryDetail();
        dictionaryDetail2.setDetailId(dictId);
        dictionaryDetail2.setSeq(afterSeq);
        this.dictionaryDetailMapper.updateById(dictionaryDetail2);

        /**
         *将下一行的seq置为当前的seq
         */
        DictionaryDetail dictionaryDetail1 = new DictionaryDetail();
        dictionaryDetail1.setDetailId(afterId);
        dictionaryDetail1.setSeq(currentSeq);
        this.dictionaryDetailMapper.updateById(dictionaryDetail1);

        return ReturnInfo.success("移动成功");

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
            dictionaryDetailPage.addOrder(OrderItem.asc("seq"));

        } else {
            dictionaryDetailPage.addOrder(OrderItem.desc("seq"));
        }
        IPage<DictionaryDetail> dictionaryDetailIPage = dictionaryDetailMapper.getDictDetailByPage(dictionaryDetailPage, dictCode);
        return ReturnInfo.success("查询成功", dictionaryDetailIPage.getRecords());
    }

    @Override
    public ReturnInfo getDictDetailByDictName(String dictname) {
        List<DictionaryDetail> dictionaryDetails = this.dictionaryDetailMapper.getDictDetailByDictName(dictname);
        return ReturnInfo.success("查询成功", dictionaryDetails);
    }

    @Override
    public ReturnInfo getSchool() {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("detail_id", "name").eq("dict_code", "School");
        List res = this.dictionaryDetailMapper.selectList(queryWrapper);
        return ReturnInfo.success("查询成功", res);
    }

    @Override
    public ReturnInfo getCollege(Integer parentId) {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("detail_id", "name").eq("parent", parentId);
        List res = this.dictionaryDetailMapper.selectList(queryWrapper);
        return ReturnInfo.success("查询成功", res);
    }

    @Override
    public ReturnInfo getTerm() {
        QueryWrapper<DictionaryDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name").eq("dict_code", "Term");
        List res = this.dictionaryDetailMapper.selectList(queryWrapper);
        System.out.println(res.get(0));
        return ReturnInfo.success("查询成功", res);
    }
}
