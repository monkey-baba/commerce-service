package com.mbb.order.adapter;

import com.github.pagehelper.PageInfo;
import com.mbb.product.api.ProductApi;
import com.mbb.product.common.dto.SkuData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductAdapter {

    @Autowired
    private ProductApi productApi;

    public PageInfo<SkuData> getSkus(String code, String name, Integer pageNum,
            Integer pageSize) {
        return productApi.getSkuPage(code, name, pageNum, pageSize);
    }


}
