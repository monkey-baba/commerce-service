package com.mbb.product.rest.controller;


import com.mbb.product.biz.data.ProductData;
import com.mbb.product.biz.data.ProductQuery;
import com.mbb.product.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/product")
    public String product() {
        return localService.local();
    }

    @GetMapping("/list")
    public List<ProductData> getProducts(ProductQuery productQuery) {
        return localService.getProducts(productQuery);
    }


}
