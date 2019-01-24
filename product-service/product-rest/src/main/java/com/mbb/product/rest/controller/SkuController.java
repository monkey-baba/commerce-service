package com.mbb.product.rest.controller;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.product.adapter.ProductServiceAdapter;
import com.mbb.product.rest.data.product.ProductData;

import com.mbb.product.biz.model.SkuModel;
import com.mbb.product.biz.service.SkuService;
import com.mbb.product.common.dto.SkuData;
import com.mbb.product.rest.data.SkuCreateData;
import com.mbb.product.rest.data.SkuUpdateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/sku")
@Slf4j
public class SkuController extends BaseController {
    @Resource
    private SkuService skuService;
    @Autowired
    private IdService idService;
    @Autowired
    private ProductServiceAdapter productServiceAdapter;
    @GetMapping("/list")
        public ResponseEntity getSkus(@RequestParam("skuId") String skuId, @RequestParam("skuName") String skuName) {
        SkuModel sku = new SkuModel();
        sku.setCode(skuId);
        sku.setName(skuName);
        //查询数据
        List<SkuModel> skus = skuService.getSkus(sku);
        //从model转data
        List<SkuData> result = dealResult(skus);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{skuId}")
    public ResponseEntity getSkus(@PathVariable("skuId") String skuId) {
        SkuModel sku = new SkuModel();
        sku.setCode(skuId);
        //查询数据
        SkuModel skuModel = skuService.findSkuByCode(skuId);
        //从model转data
        SkuData result = dealResult(skuModel);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/skuspeclist")
    public ResponseEntity getSkuSpecList() {
        List<DictValueData> skuSpecList = productServiceAdapter.getSkuSpec();
        return ResponseEntity.ok(skuSpecList);
    }


    @PostMapping("/update")
    public ResponseEntity updateSku(@RequestBody SkuUpdateData data) throws Exception {
        SkuModel sku = skuService.findSkuById(data.getId());
        sku.setCode(data.getSkuId());
        sku.setName(data.getSkuName());
        skuService.updateSku(sku);
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody SkuCreateData data) {
        SkuModel skuModel = new SkuModel();
        skuModel.setCode(data.getSkuId());
        skuModel.setName(data.getSkuName());
        skuModel.setId(idService.genId());
        skuService.createSku(skuModel);
        return ResponseEntity.ok(dealResult(skuModel));
    }

    private List<SkuData> dealResult(List<SkuModel> skus) {
        List<SkuData> skuDataList = skus.stream().map(skuModel -> dealResult(skuModel)).collect(Collectors.toList());
        return skuDataList;
    }

    private SkuData dealResult(SkuModel skuModel) {

            SkuData skuData = new SkuData();
            //sku编号
            skuData.setSkuId(skuModel.getCode());
            //sku名称
            skuData.setSkuName(skuModel.getName());
            //id
            skuData.setId(skuModel.getId());
            return skuData;

    }
}
