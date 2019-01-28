package com.mbb.product.biz.service.impl;


import com.mbb.product.biz.dao.PriceMapper;

import com.mbb.product.biz.model.PriceModel;
import com.mbb.product.biz.service.PriceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class PriceServiceImpl implements PriceService {

    @Resource
    private PriceMapper priceMapper;

    @Override
    public List<PriceModel> getPrices(PriceModel priceModel) {
        //封装查询Example
        Example example = mapQueryInfo(priceModel);
        List<PriceModel> priceModels = priceMapper.selectByExample(example);
        log.info("price size====" + priceModels.size());
        return priceModels;
    }

    @Override
    public void deletePrice(Long id) {
        priceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PriceModel findPriceById(Long id) {
        return this.priceMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateprice(PriceModel price) throws Exception {
        int result = this.priceMapper.updateByPrimaryKey(price);
        if (result == 0) {
            throw new Exception("更新失败！");
        }
    }

    @Override
    public PriceModel createPrice(PriceModel price) {
        this.priceMapper.insert(price);
        return price;
    }

    private Example mapQueryInfo(PriceModel priceModel) {
        //价目表名称
        String name = priceModel.getName();
        //渠道号
        List<Long> channelId = priceModel.getChannelId();
        Example example = new Example(PriceModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        if (!channelId.isEmpty()) {
            criteria.andLike("channelId","%" + channelId + "%");
        }
        return example;
    }



}
