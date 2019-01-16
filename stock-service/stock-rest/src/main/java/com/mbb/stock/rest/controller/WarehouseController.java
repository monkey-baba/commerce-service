package com.mbb.stock.rest.controller;

import com.mbb.stock.biz.dto.StockInfoDto;
import com.mbb.stock.biz.dto.StockQueryDto;
import com.mbb.stock.biz.dto.WarehouseInfoDto;
import com.mbb.stock.biz.dto.WarehouseQueryDto;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.model.WarehouseModel;
import com.mbb.stock.biz.service.StockService;
import com.mbb.stock.biz.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-10 17:41
 */
@RestController
@RequestMapping("api/v1/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/info")
    public ResponseEntity getWarehouses(@RequestBody WarehouseQueryDto warehouseQueryDto) {
        List<WarehouseInfoDto> warehouseInfoDtoList = warehouseService.getWarehouses(warehouseQueryDto);
        return ResponseEntity.ok(warehouseInfoDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity addWarehouse(@RequestBody List<WarehouseInfoDto> warehouseInfoDtoList) {
        warehouseService.addWarehouse(warehouseInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteWarehouse(@RequestParam(value = "id", required = true) String id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
