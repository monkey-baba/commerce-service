package com.mbb.product.rest.controller;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.product.adapter.ProductServiceAdapter;
import com.mbb.product.biz.service.PriceRowService;
import com.mbb.product.biz.model.PriceRowModel;
import com.mbb.product.rest.data.price.PriceDeleteData;
import com.mbb.product.rest.data.pricerow.PriceRowCreateData;
import com.mbb.product.rest.data.pricerow.PriceRowData;
import com.mbb.product.rest.data.pricerow.PriceRowQuery;
import com.mbb.product.rest.data.pricerow.PriceRowUpdateData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/pricerow")
@Slf4j
public class PriceRowController extends BaseController {
    @Resource
    private PriceRowService priceRowService;
    @Autowired
    private IdService idService;
    @Autowired
    private ProductServiceAdapter productServiceAdapter;

    @GetMapping("/list/{priceid}")
    public ResponseEntity getPriceRows(@PathVariable(name="priceid") Long priceid) {
        PriceRowModel priceRowModel = new PriceRowModel();
        priceRowModel.setPriceId(priceid);
        //查询数据
        List<PriceRowModel> priceRowModels = priceRowService.getPriceRows(priceRowModel);
        //从model转data
        List<PriceRowData> result = dealResult(priceRowModels);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/pricetypelist")
    public ResponseEntity getPriceTypeList() {
        List<DictValueData> priceTypeList = productServiceAdapter.getPriceType();
        return ResponseEntity.ok(priceTypeList);
    }

    @PostMapping("/update")
    public ResponseEntity updateprice(@RequestBody PriceRowUpdateData data) throws Exception {
        PriceRowModel priceRowModel = priceRowService.findPriceRowById(data.getId());
        priceRowModel.setPrice(data.getPrice());
        priceRowModel.setStartTime(data.getStartTime());
        priceRowModel.setEndTime(data.getEndTime());
        priceRowModel.setPriceTypeId(data.getPriceTypeId());
        priceRowService.updatePriceRow(priceRowModel);
        return ResponseEntity.ok("更新成功");
}

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PriceRowCreateData data) {
        PriceRowModel priceRowModel = new PriceRowModel();
        priceRowModel.setPriceId(data.getPriceId());
        priceRowModel.setPrice(data.getPrice());
        priceRowModel.setStartTime(data.getStartTime());
        priceRowModel.setEndTime(data.getEndTime());
        priceRowModel.setId(idService.genId());
        priceRowModel.setSkuId(data.getSkuId());
        priceRowModel.setPriceTypeId(data.getPriceTypeId());
        priceRowService.createPriceRow(priceRowModel);
        return ResponseEntity.ok(dealResult(priceRowModel));
    }




    @PostMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestBody List<PriceDeleteData> datas) {
        for (PriceDeleteData data : datas) {
            priceRowService.deletePriceRow(data.getId());
        }
        return ResponseEntity.ok("删除成功");
    }


    private List<PriceRowData> dealResult(List<PriceRowModel> priceRowModels) {
        List<PriceRowData> priceRowDatas = priceRowModels.stream().map(priceRowModel -> dealResult(priceRowModel)).collect(Collectors.toList());
        return priceRowDatas;
    }

    private PriceRowData dealResult(PriceRowModel priceRowModel){
        PriceRowData priceRowData = new PriceRowData();
        //价目表id
        priceRowData.setPriceId(priceRowModel.getPriceId());
        //
        priceRowData.setPrice(priceRowModel.getPrice());
        //id
        priceRowData.setPriceTypeId(priceRowModel.getPriceTypeId());
        //是否启用
        priceRowData.setSkuId(priceRowModel.getSkuId());
        //开始时间
        priceRowData.setStartTime(priceRowModel.getStartTime());
        //结束时间
        priceRowData.setEndTime(priceRowModel.getEndTime());
        return priceRowData;
    }
}
