package com.mbb.stock.rest.controller;

import com.mbb.stock.biz.dto.StoreInfoDto;
import com.mbb.stock.biz.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.mbb.stock.biz.dto.StoreListQuery;

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
        List<StoreInfoDto> stockInfoDtoList = storeService.getStores(storeListQuery);
        return ResponseEntity.ok(stockInfoDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity addStock(@RequestBody List<StoreInfoDto> stockInfoDtoList) {
        storeService.addStore(stockInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
