package com.mbb.product.biz.service.impl;

import com.mbb.product.biz.data.ProductData;
import com.mbb.product.biz.data.ProductQuery;
import com.mbb.product.biz.service.LocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LocalServiceImpl  implements LocalService {


    @Override
    public String local() {
        // Service方法可以调用Mybatis Dao 实现 数据库逻辑，此处忽略
        return "This is a demo";
    }

    @Override
    public List<ProductData> getProducts(ProductQuery productQuery) {
        ProductData productData = new ProductData();
        productData.setCode("0001");
        productData.setName("测试商品1");
        List<ProductData> products = new ArrayList<>();
        products.add(productData);
        return products;
    }
}
