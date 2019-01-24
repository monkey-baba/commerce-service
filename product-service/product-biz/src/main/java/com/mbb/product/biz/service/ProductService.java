package com.mbb.product.biz.service;

import com.mbb.product.biz.model.ProductModel;

import java.util.List;

public interface ProductService {

    List<ProductModel> getProducts(ProductModel productModel);

    void deleteProduct(Long id);
    ProductModel findProductById(Long id);
    void updateProduct(ProductModel product) throws Exception;
    ProductModel createProduct(ProductModel product);
}
