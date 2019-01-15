package com.mbb.product.biz.service;


import com.mbb.product.biz.data.ProductData;
import com.mbb.product.biz.data.ProductQuery;

import java.util.List;

/**
 * 本地Service处理类
 */
public interface LocalService {

    String local();
    List<ProductData> getProducts(ProductQuery productQuery);
}
