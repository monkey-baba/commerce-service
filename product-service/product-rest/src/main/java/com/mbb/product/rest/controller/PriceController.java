package com.mbb.product.rest.controller;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.product.biz.data.PriceCreateData;
import com.mbb.product.biz.data.PriceData;
import com.mbb.product.biz.data.PriceQuery;
import com.mbb.product.biz.data.PriceUpdateData;
import com.mbb.product.biz.model.PriceModel;
import com.mbb.product.biz.service.PriceService;
import com.mbb.product.rest.data.PriceDeleteData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/v1/price")
@Slf4j
public class PriceController extends BaseController {
    @Resource
    private PriceService priceService;
    @Autowired
    private IdService idService;
    @GetMapping("/list")
    public ResponseEntity getPrices(PriceQuery priceQuery) {
        return ResponseEntity.ok(priceService.getPrices(priceQuery));
    }

    @PostMapping("/update")
    public ResponseEntity updateprice(@RequestBody PriceUpdateData data) throws Exception {
        PriceModel price = priceService.findPriceById(data.getId());
        price.setName(data.getName());
        price.setChannelId(data.getChannelId());
        price.setActive(data.getActive());
        price.setStartTime(data.getStartTime());
        price.setEndTime(data.getEndTime());
        price.setPriority(data.getPriority());
        priceService.updateprice(price);
        return ResponseEntity.ok("更新成功");
}

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PriceCreateData data) {
        PriceModel price = new PriceModel();
        price.setName(data.getName());
        price.setChannelId(data.getChannelId());
        price.setStartTime(data.getStartTime());
        price.setEndTime(data.getEndTime());
        price.setId(idService.genId());
        price.setActive(data.getActive());
        price.setPriority(data.getPriority());
        priceService.createPrice(price);
        PriceData resp = new PriceData();
        resp.setName(price.getName());
        resp.setChannelId(price.getChannelId());
        resp.setStartTime(price.getStartTime());
        resp.setEndTime(price.getEndTime());

        return ResponseEntity.ok(resp);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestBody List<PriceDeleteData> datas) {
        for (PriceDeleteData data : datas) {
            priceService.deletePrice(data.getId());
        }
        return ResponseEntity.ok("删除成功");
    }
}
