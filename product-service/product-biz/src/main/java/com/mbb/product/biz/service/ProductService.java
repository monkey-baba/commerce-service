package com.mbb.product.biz.service;

import com.github.pagehelper.PageInfo;
import com.mbb.product.biz.data.ProductData;
import com.mbb.product.biz.data.ProductQuery;
import com.mbb.product.biz.model.ProductModel;

public interface ProductService {
    PageInfo<ProductData> getProducts(ProductQuery productQuery);
    void deleteProduct(Long id);
    ProductModel findProductById(Long id);
    void updateProduct(ProductModel product) throws Exception;
    ProductModel createProduct(ProductModel product);
}
