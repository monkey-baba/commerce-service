package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.stock.biz.dto.StoreInfoDto;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.service.StoreService;
import com.mbb.stock.rest.dto.StockInfoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.mbb.stock.biz.dto.StoreListQuery;
import com.mbb.stock.biz.model.PointOfServiceModel;
@RestController
@RequestMapping("/api/v1/store")
public class StoreController extends BaseController{
    @Autowired
    private StoreService storeService;
    @GetMapping("/allList")
    public ResponseEntity storeAllList(StoreListQuery query) {
        List<StoreInfoDto> stockInfoDtoList = storeService.getAllStores();
        return ResponseEntity.ok(stockInfoDtoList);
    }
    @PostMapping("/info")
    public ResponseEntity getStores(@RequestBody StoreListQuery storeListQuery) {

        PointOfServiceModel storeModel = new PointOfServiceModel();
        storeModel.setName(storeListQuery.getName());
        storeModel.setCode(storeListQuery.getCode());

        if(!(storeListQuery.getClassification().equals(""))){
            storeModel.setClassifyId(Long.valueOf(storeListQuery.getClassification()));
        }
        if(!(storeListQuery.getStatus().equals(""))){
            storeModel.setStatusId(Long.valueOf(storeListQuery.getStatus()));
        }
        storeModel.setOwner(storeListQuery.getPeople());
        //开启分页
        PageHelper.startPage(storeListQuery.getPageNum(), storeListQuery.getPageSize());
        //查询数据
        List<PointOfServiceModel> stocks = storeService.getStores(storeModel);
        //获取页码等信息
        PageInfo<PointOfServiceModel> origin = PageInfo.of(stocks);
        //从model转data
        List<StoreInfoDto> stockInfoRespList = dealResult(origin);
        //用data生成新的分页数据
        PageInfo<StoreInfoDto> result = PageInfo.of(stockInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity addStock(@RequestBody List<StoreInfoDto> stockInfoDtoList) {
        storeService.addStore(stockInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    private List<StoreInfoDto> dealResult(PageInfo<PointOfServiceModel> stores) {
        List<StoreInfoDto> storeInfoRespList = stores.getList().stream().map(store -> {
            StoreInfoDto storeInfoResp = new StoreInfoDto();
            //门店地址
            Long address=store.getAddressId();
            storeInfoResp.setAddress(address);
            //门店名字
            storeInfoResp.setName(store.getName());
            //门店状态
            Long status=store.getAddressId();
            storeInfoResp.setStatus(status);
            //门店负责人
            String owner=store.getOwner();
            storeInfoResp.setOwner(String.valueOf(owner == null ? "" : owner));

            return storeInfoResp;
        }).collect(Collectors.toList());
        return storeInfoRespList;
    }
}
