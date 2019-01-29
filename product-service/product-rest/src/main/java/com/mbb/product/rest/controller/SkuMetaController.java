package com.mbb.product.rest.controller;


import com.mbb.product.biz.model.SkuMetaModel;

import com.mbb.product.biz.service.SkuMetaService;
import com.mbb.product.rest.data.sku.SkuMetaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/skumeta")
@Slf4j
public class SkuMetaController extends BaseController {
    @Resource
    private SkuMetaService skuMetaService;

    @GetMapping("/list/{specid}")
    public ResponseEntity getSkuMetas(@PathVariable(name="specid") Long specId) {

       SkuMetaModel skuMetaModel = new SkuMetaModel();
        skuMetaModel.setSpecId(specId);
        return ResponseEntity.ok(dealResult(skuMetaService.getSkuMetaModels(skuMetaModel)));
    }

    private List<SkuMetaData> dealResult(List<SkuMetaModel> skuMetaModels) {
        return skuMetaModels.stream().map(skuMetaModel -> dealResult(skuMetaModel)).collect(Collectors.toList());
    }

    private SkuMetaData dealResult(SkuMetaModel skuMetaModel) {

        SkuMetaData skuMetaData = new SkuMetaData();
        //名称
        skuMetaData.setName(skuMetaModel.getName());
        //id
        skuMetaData.setId(skuMetaModel.getId());

        return skuMetaData;
    }
}
