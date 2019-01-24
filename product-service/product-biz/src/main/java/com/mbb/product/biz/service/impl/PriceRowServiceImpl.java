package com.mbb.product.biz.service.impl;

import com.mbb.product.biz.dao.PriceRowMapper;

import com.mbb.product.biz.model.PriceModel;
import com.mbb.product.biz.model.PriceRowModel;
import com.mbb.product.biz.service.PriceRowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class PriceRowServiceImpl implements PriceRowService {

    @Resource
    private PriceRowMapper priceRowMapper;
    @Override
    public List<PriceRowModel> getPriceRows(PriceRowModel priceRowModel) {
        //封装查询Example
        Example example = mapQueryInfo(priceRowModel);
        List<PriceRowModel> priceRowModels = priceRowMapper.selectByExample(example);
        log.info("pricerow size====" + priceRowModels.size());
        return priceRowModels;
    }



    @Override
    public void deletePriceRow(Long id) {
        priceRowMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PriceRowModel findPriceRowById(Long id) {
        return this.priceRowMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePriceRow(PriceRowModel priceRowModel) throws Exception {
        int result = this.priceRowMapper.updateByPrimaryKey(priceRowModel);
        if (result == 0) {
            throw new Exception("更新失败！");
        }
    }

    @Override
    public PriceRowModel createPriceRow(PriceRowModel priceRowModel) {
        this.priceRowMapper.insert(priceRowModel);
        return priceRowModel;
    }

    private Example mapQueryInfo(PriceRowModel priceRowModel) {
        //价目表id
        Long priceId = priceRowModel.getPriceId();
        Example example = new Example(PriceModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (priceId!=null) {
            criteria.andEqualTo("priceId", priceId);
        }
        return example;
    }

}
