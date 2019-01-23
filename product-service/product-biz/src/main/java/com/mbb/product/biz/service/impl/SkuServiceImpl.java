package com.mbb.product.biz.service.impl;


import com.mbb.product.biz.dao.SkuMapper;
import com.mbb.product.biz.model.ProductModel;
import com.mbb.product.biz.model.SkuModel;
import com.mbb.product.biz.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class SkuServiceImpl implements SkuService {
    private static final Logger LOG = LoggerFactory.getLogger(SkuServiceImpl.class);

    @Resource
    private SkuMapper skuMapper;
    @Override
    public List<SkuModel> getSkus(SkuModel sku) {

        //封装查询Example
        Example example = mapQueryInfo(sku);
        List<SkuModel> skus = skuMapper.selectByExample(example);
        LOG.info("sku size====" + skus.size());
        return skus;
    }


    @Override
    public void deleteSku(Long id) {
        skuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SkuModel findSkuById(Long id) {
        return this.skuMapper.selectByPrimaryKey(id);
    }

    @Override
    public SkuModel findSkuByCode(String skuId) {
        Example example = new Example(SkuModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", skuId);
        return skuMapper.selectOneByExample(example);
    }

    @Override
    public void updateSku(SkuModel sku) throws Exception {
        int result = this.skuMapper.updateByPrimaryKey(sku);
        if (result == 0) {
            throw new Exception("更新失败！");
        }
    }

    @Override
    public SkuModel createSku(SkuModel sku) {
        this.skuMapper.insert(sku);
        return sku;
    }

    private Example mapQueryInfo(SkuModel sku) {
        //产品编号
        String code = sku.getCode();
        //产品名称
        String name = sku.getName();
        Example example = new Example(ProductModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        return example;
    }

}
