package com.mbb.product.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.product.adapter.ProductServiceAdapter;
import com.mbb.product.rest.data.price.PriceCreateData;
import com.mbb.product.rest.data.price.PriceData;
import com.mbb.product.rest.data.price.PriceQuery;
import com.mbb.product.rest.data.price.PriceUpdateData;
import com.mbb.product.biz.model.PriceModel;
import com.mbb.product.biz.service.PriceService;
import com.mbb.product.rest.data.price.PriceDeleteData;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/price")
@Slf4j
public class PriceController extends BaseController {
    @Resource
    private PriceService priceService;
    @Autowired
    private IdService idService;
    @Autowired
    private ProductServiceAdapter productServiceAdapter;

    @GetMapping("/list")
    public ResponseEntity getPrices(PriceQuery priceQuery) {
        PriceModel priceModel = new PriceModel();
        priceModel.setName(priceQuery.getName());
        priceModel.setChannelId(priceQuery.getChannelId());
        //开启分页
        PageHelper.startPage(priceQuery.getPageNum(), priceQuery.getPageSize());

        //用data生成新的分页数据
        PageInfo<PriceData> result = PageInfo.of(dealResult(priceService.getPrices(priceModel)));
        result.setTotal(result.getTotal());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/channellist")
    public ResponseEntity getChannelList() {
        List<DictValueData> channelList = productServiceAdapter.getChannel();
        return ResponseEntity.ok(channelList);
    }

    @PostMapping("/update")
    public ResponseEntity updateprice(@RequestBody PriceUpdateData data) throws Exception {
        PriceModel priceModel = priceService.findPriceById(data.getId());
        priceModel.setName(data.getName());
        priceModel.setChannelId(data.getChannelId());
        priceModel.setActive(data.getActive());
        priceModel.setStartTime(data.getStartTime());
        priceModel.setEndTime(data.getEndTime());
        priceModel.setPriority(data.getPriority());
        priceService.updateprice(priceModel);
        return ResponseEntity.ok("更新成功");
}

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody PriceCreateData data) {
        PriceModel priceModel = new PriceModel();
        priceModel.setName(data.getName());
        priceModel.setChannelId(data.getChannelId());
        priceModel.setStartTime(data.getStartTime());
        priceModel.setEndTime(data.getEndTime());
        priceModel.setId(idService.genId());
        priceModel.setActive(data.getActive());
        priceModel.setPriority(data.getPriority());
        priceService.createPrice(priceModel);
        return ResponseEntity.ok(dealResult(priceModel));
    }

    @PostMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestBody List<PriceDeleteData> datas) {
        for (PriceDeleteData data : datas) {
            priceService.deletePrice(data.getId());
        }
        return ResponseEntity.ok("删除成功");
    }

    private List<PriceData> dealResult(List<PriceModel> prices) {
        List<PriceData> priceDataList = prices.stream().map(priceModel -> dealResult(priceModel)).collect(Collectors.toList());
        return priceDataList;
    }

    private PriceData dealResult(PriceModel priceModel){
        PriceData priceData = new PriceData();
        //价目表名称
        priceData.setName(priceModel.getName());
        //渠道列表
        priceData.setChannelId(priceModel.getChannelId());
        //id
        priceData.setId(priceModel.getId());
        //是否启用
        priceData.setActive(priceModel.getActive());
        //开始时间
        priceData.setStartTime(priceModel.getStartTime());
        //结束时间
        priceData.setEndTime(priceModel.getEndTime());
        //优先级
        priceData.setPriority(priceModel.getPriority());
        return priceData;
    }

}
