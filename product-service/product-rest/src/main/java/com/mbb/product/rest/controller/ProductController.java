package com.mbb.product.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.product.adapter.ProductServiceAdapter;
import com.mbb.product.rest.data.product.ProductCreateData;
import com.mbb.product.rest.data.product.ProductData;
import com.mbb.product.rest.data.product.ProductQuery;
import com.mbb.product.rest.data.product.ProductUpdateData;
import com.mbb.product.biz.model.ProductModel;
import com.mbb.product.biz.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/product")
@Slf4j
public class ProductController extends BaseController {
    @Resource
    private ProductService productService;
    @Autowired
    private IdService idService;
    @Autowired
    private ProductServiceAdapter productServiceAdapter;
    @GetMapping("/list")
    public ResponseEntity getProducts(ProductQuery productQuery) {
        ProductModel productModel = new ProductModel();
        productModel.setCode(productQuery.getCode());
        productModel.setName(productQuery.getName());
        productModel.setChannelId(productQuery.getChannelId());
        productModel.setApprovedId(productQuery.getApprovedId());

        //开启分页
        PageHelper.startPage(productQuery.getPageNum(), productQuery.getPageSize());
        //用data生成新的分页数据
        PageInfo<ProductData> result = PageInfo.of(dealResult(productService.getProducts(productModel)));
        result.setTotal(result.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    public ResponseEntity updateProduct(@RequestBody ProductUpdateData data) throws Exception {
        ProductModel productModel = productService.findProductById(data.getId());
        productModel.setCode(data.getCode());
        productModel.setName(data.getName());
        productModel.setChannelId(data.getChannelId());
        productModel.setApprovedId(data.getApprovedId());
        productService.updateProduct(productModel);
        return ResponseEntity.ok("更新成功");
}

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody ProductCreateData data) {
        ProductModel productModel = new ProductModel();
        productModel.setCode(data.getCode());
        productModel.setName(data.getName());
        productModel.setChannelId(data.getChannelId());
        productModel.setApprovedId(data.getApprovedId());
        productModel.setId(idService.genId());
        productService.createProduct(productModel);
        return ResponseEntity.ok(dealResult(productModel));
    }

    @GetMapping("/approvedstatuslist")
    public ResponseEntity getApprovedStatusList() {
        List<DictValueData> approvedStatusList = productServiceAdapter.getApprovedStatus();
        return ResponseEntity.ok(approvedStatusList);
    }

    @GetMapping("/channellist")
    public ResponseEntity getChannelList() {
        List<DictValueData> channelList = productServiceAdapter.getChannel();
        return ResponseEntity.ok(channelList);
    }

    @GetMapping("/productattrlist")
    public ResponseEntity getProductAttrList() {
        List<DictValueData> productAttrList = productServiceAdapter.getProductAttr();
        return ResponseEntity.ok(productAttrList);
    }

    @GetMapping("/unitlist")
    public ResponseEntity getUnitList() {
        List<DictValueData> unitList = productServiceAdapter.getUnit();
        return ResponseEntity.ok(unitList);
    }

    private List<ProductData> dealResult(List<ProductModel> products) {
        List<ProductData> productDataList = products.stream().map(productModel -> dealResult(productModel)).collect(Collectors.toList());
        return productDataList;
    }



    private ProductData dealResult(ProductModel productModel) {
        ProductData productData = new ProductData();
        //产品编码
        productData.setCode(productModel.getCode());
        //产品名称
        productData.setName(productModel.getName());
        //销售渠道
        productData.setChannelId(productModel.getChannelId());
        //id
        productData.setId(productModel.getId());
        //
        productData.setApprovedId(productModel.getApprovedId());
        return productData;
    }

}
