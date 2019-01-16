package com.mbb.stock.rest.controller;

import com.mbb.stock.biz.dto.StockInfoDto;
import com.mbb.stock.biz.dto.StockQueryDto;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:33
 */
@RestController
@RequestMapping("api/v1/stock")
public class StockController extends BaseController {

    @Autowired
    private StockService stockService;

    @GetMapping("/query")
    public ResponseEntity getAllStocks() {
        List<StockInfoDto> stockInfoDtoList = stockService.getAllStocks();
        return ResponseEntity.ok(stockInfoDtoList);
    }

    @PostMapping("/info")
    public ResponseEntity getStocks(@RequestBody StockQueryDto stockQueryDto) {
        List<StockInfoDto> stockInfoDtoList = stockService.getStocks(stockQueryDto);
        return ResponseEntity.ok(stockInfoDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity addStock(@RequestBody List<StockInfoDto> stockInfoDtoList) {
        stockService.addStock(stockInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteStock(@RequestParam(value = "id", required = true) String id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
