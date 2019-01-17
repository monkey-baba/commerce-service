package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.service.StockService;
import com.mbb.stock.rest.dto.StockCreateData;
import com.mbb.stock.rest.dto.StockInfoResp;
import com.mbb.stock.rest.dto.StockListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IdService idService;

    @GetMapping("/info")
    public ResponseEntity getStocks(StockListQuery stockListQuery) {
        StockModel stockModel = new StockModel();
        stockModel.setSkuId(stockListQuery.getSkuId());
        stockModel.setWarehouseId(stockListQuery.getWarehouseId());

        //开启分页
        PageHelper.startPage(stockListQuery.getPageNum(), stockListQuery.getPageSize());
        //查询数据
        List<StockModel> stocks = stockService.getStocks(stockModel);
        //获取页码等信息
        PageInfo<StockModel> origin = PageInfo.of(stocks);
        //从model转data
        List<StockInfoResp> stockInfoRespList = origin.getList().stream().map(stock -> {
            StockInfoResp stockInfoResp = new StockInfoResp();
            convert(stock, stockInfoResp);
            return stockInfoResp;
        }).collect(Collectors.toList());
        //用data生成新的分页数据
        PageInfo<StockInfoResp> result = PageInfo.of(stockInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity createStock(@RequestBody StockCreateData stockCreateData) {
        StockModel stockModel = new StockModel();
        stockModel.setId(idService.genId());
        stockModel.setSkuId(stockCreateData.getSkuId());
        stockModel.setWarehouseId(stockCreateData.getWarehouseId());
        stockModel.setAvailable(stockCreateData.getAvailable());
        stockModel.setVersion(0);
        stockService.createStock(stockModel);
        StockInfoResp stockInfoResp = new StockInfoResp();
        convert(stockModel, stockInfoResp);
        return ResponseEntity.ok(stockInfoResp);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteStock(@RequestParam(value = "id", required = true) String id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok("删除成功");
    }

    private StockInfoResp convert(StockModel stockModel, StockInfoResp stockInfoResp) {
        //主键
        stockInfoResp.setId(stockModel.getId());
        //商品编码
        stockInfoResp.setSkuId(stockModel.getSkuId());
        //商品名称
        //stockInfoResp.setSkuName();
        //仓库编码
        stockInfoResp.setWarehouseId(stockModel.getWarehouseId());
        //可用量
        stockInfoResp.setAvailable(stockModel.getAvailable());
        return stockInfoResp;
    }

}
