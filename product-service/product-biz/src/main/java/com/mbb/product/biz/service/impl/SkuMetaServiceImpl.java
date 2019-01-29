package com.mbb.product.biz.service.impl;


import com.mbb.product.biz.dao.SkuMetaMapper;
import com.mbb.product.biz.model.CategoryModel;
import com.mbb.product.biz.model.SkuMetaModel;
import com.mbb.product.biz.service.SkuMetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service
@Slf4j
public class SkuMetaServiceImpl implements SkuMetaService {

    @Resource
    private SkuMetaMapper skuMetaMapper;

    private Example mapQueryInfo(SkuMetaModel skuMetaModel) {

        Long  specId = skuMetaModel.getSpecId();
        Example example = new Example(SkuMetaModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (specId!=null) {
            criteria.andEqualTo("specId", specId);
        }else {
            criteria.andIsNull("specId");
        }
        return example;
    }


    @Override
    public List<SkuMetaModel> getSkuMetaModels(SkuMetaModel skuMetaModel) {
        //封装查询Example
        Example example = mapQueryInfo(skuMetaModel);
        List<SkuMetaModel> skuMetaModels = skuMetaMapper.selectByExample(example);
        log.info("skuMeta size====" + skuMetaModels.size());
        return skuMetaModels;
    }
}
