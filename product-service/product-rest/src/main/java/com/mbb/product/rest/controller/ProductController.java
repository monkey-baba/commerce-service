package com.mbb.product.rest.controller;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.product.biz.data.ProductCreateData;
import com.mbb.product.biz.data.ProductData;
import com.mbb.product.biz.data.ProductQuery;
import com.mbb.product.biz.data.ProductUpdateData;
import com.mbb.product.biz.model.ProductModel;
import com.mbb.product.biz.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/v1/product")
@Slf4j
public class ProductController extends BaseController {
    @Resource
    private ProductService productService;
    @Autowired
    private IdService idService;
    @GetMapping("/list")
    public ResponseEntity getProducts(ProductQuery productQuery) {
        return ResponseEntity.ok(productService.getProducts(productQuery));
    }

    @PostMapping("/update")
    public ResponseEntity updateProduct(@RequestBody ProductUpdateData data) throws Exception {
        ProductModel product = productService.findProductById(data.getId());
        product.setCode(data.getCode());
        product.setName(data.getName());
        product.setChannelId(data.getChannelId());
        product.setApprovedId(data.getApprovedId());
        productService.updateProduct(product);
        return ResponseEntity.ok("更新成功");
}

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ProductCreateData data) {
        ProductModel product = new ProductModel();
        product.setCode(data.getCode());
        product.setName(data.getName());
        product.setChannelId(data.getChannelId());
        product.setApprovedId(data.getApprovedId());
        product.setId(idService.genId());
        productService.createProduct(product);
        ProductData resp = new ProductData();
        resp.setCode(product.getCode());
        resp.setName(product.getName());
        resp.setChannelId(product.getChannelId());
        return ResponseEntity.ok(resp);
    }
}
