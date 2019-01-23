package com.mbb.product.biz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.product.biz.dao.ProductMapper;
import com.mbb.product.biz.data.ProductData;
import com.mbb.product.biz.data.ProductQuery;
import com.mbb.product.biz.model.ProductModel;
import com.mbb.product.biz.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;
    @Override
    public PageInfo<ProductData> getProducts(ProductQuery productQuery) {
        ProductModel productModel = new ProductModel();
        productModel.setCode(productQuery.getCode());
        productModel.setName(productQuery.getName());
        productModel.setChannelId(productQuery.getChannelId());
        productModel.setApprovedId(productQuery.getApprovedId());

        //开启分页
        PageHelper.startPage(productQuery.getPageNum(), productQuery.getPageSize());
        //查询数据
        List<ProductModel> products = getProducts(productModel);
        //获取页码等信息
        PageInfo<ProductModel> origin = PageInfo.of(products);
        //从model转data
        List<ProductData> productDataList = dealResult(origin);
        //用data生成新的分页数据
        PageInfo<ProductData> result = PageInfo.of(productDataList);
        result.setTotal(origin.getTotal());
        return result;
    }

    public List<ProductModel> getProducts(ProductModel productModel) {
        //封装查询Example
        Example example = mapQueryInfo(productModel);
        List<ProductModel> productModels = productMapper.selectByExample(example);
        log.info("product size====" + productModels.size());
        return productModels;
    }

    @Override
    public void deleteProduct(Long id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProductModel findProductById(Long id) {
        return this.productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateProduct(ProductModel product) throws Exception {
        int result = this.productMapper.updateByPrimaryKey(product);
        if (result == 0) {
            throw new Exception("更新失败！");
        }
    }

    @Override
    public ProductModel createProduct(ProductModel product) {
        this.productMapper.insert(product);
        return product;
    }

    private Example mapQueryInfo(ProductModel productModel) {
        //产品编号
        String code = productModel.getCode();
        //产品名称
        String name = productModel.getName();
        //渠道号
        Long channelId = productModel.getChannelId();
        Example example = new Example(ProductModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        if (channelId!=null) {
        }
        return example;
    }


    private List<ProductData> dealResult(PageInfo<ProductModel> products) {
        List<ProductData> productDataList = products.getList().stream().map(productModel -> {
            ProductData productData = new ProductData();
            //客户编号
            productData.setCode(productModel.getCode());
            //客户名称
            productData.setName(productModel.getName());
            //手机号
            productData.setChannelId(productModel.getChannelId());
            //id
            productData.setId(productModel.getId());
            //
            productData.setApprovedId(productModel.getApprovedId());
            return productData;
        }).collect(Collectors.toList());
        return productDataList;
    }
}
