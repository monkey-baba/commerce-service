package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.basic.common.dto.DictValueData;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.service.StoreService;
import com.mbb.stock.common.dto.StoreInfoDto;
import com.mbb.stock.rest.dto.PosQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/storePos")
public class StorePosController extends BaseController{
    @Autowired
    private StoreService storeService;
    @GetMapping("/pos/list")
    public ResponseEntity getPosList(PosQuery posQuery) {
        PointOfServiceModel storeModel = new PointOfServiceModel();
        storeModel.setName(posQuery.getName());
        storeModel.setCode(posQuery.getCode());
        //开启分页
        PageHelper.startPage(posQuery.getPageNum(), posQuery.getPageSize());
        //查询数据
        List<PointOfServiceModel> stocks = storeService.getPos(storeModel);
        //获取页码等信息
        PageInfo<PointOfServiceModel> origin = PageInfo.of(stocks);
        //从model转data
        List<StoreInfoDto> stockInfoRespList = dealResult(origin);
        //用data生成新的分页数据
        PageInfo<StoreInfoDto> result = PageInfo.of(stockInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }
    private List<StoreInfoDto> dealResult(PageInfo<PointOfServiceModel> stores) {
        List<StoreInfoDto> storeInfoRespList=null;
        if(stores.getList().size()>0){
            storeInfoRespList = stores.getList().stream().map(store -> {
                StoreInfoDto storeInfoResp = new StoreInfoDto();
                //供货点名字
                storeInfoResp.setName(store.getName());
                //供货点id
                storeInfoResp.setId(store.getId());
                //供货点编码
                storeInfoResp.setCode(store.getCode());
                return storeInfoResp;
            }).collect(Collectors.toList());
        };
        return storeInfoRespList;
    }
}
