package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.model.WarehouseModel;
import com.mbb.stock.biz.service.WarehouseService;
import com.mbb.stock.rest.dto.StockInfoResp;
import com.mbb.stock.rest.dto.WarehouseCreateData;
import com.mbb.stock.rest.dto.WarehouseInfoResp;
import com.mbb.stock.rest.dto.WarehouseListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private IdService idService;

    @GetMapping("/info")
    public ResponseEntity getWarehouses(WarehouseListQuery warehouseListQuery) {
        WarehouseModel warehouseModel = new WarehouseModel();
        warehouseModel.setCode(warehouseListQuery.getCode());
        warehouseModel.setName(warehouseListQuery.getName());

        //开启分页
        PageHelper.startPage(warehouseListQuery.getPageNum(), warehouseListQuery.getPageSize());
        //查询数据
        List<WarehouseModel> warehouses = warehouseService.getWarehouses(warehouseModel);
        //获取页码等信息
        PageInfo<WarehouseModel> origin = PageInfo.of(warehouses);
        //从model转data
        List<WarehouseInfoResp> stockInfoRespList = origin.getList().stream().map(warehouse -> {
            WarehouseInfoResp warehouseInfoResp = new WarehouseInfoResp();
            convert(warehouse, warehouseInfoResp);
            return warehouseInfoResp;
        }).collect(Collectors.toList());
        //用data生成新的分页数据
        PageInfo<WarehouseInfoResp> result = PageInfo.of(stockInfoRespList);
        result.setTotal(origin.getTotal());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    public ResponseEntity createWarehouse(@RequestBody WarehouseCreateData warehouseCreateData) {
        WarehouseModel warehouseModel = new WarehouseModel();
        warehouseModel.setId(idService.genId());
        warehouseModel.setCode(warehouseCreateData.getCode());
        warehouseModel.setName(warehouseCreateData.getName());
        //是否启用
        String active = warehouseCreateData.getActive();
        if ("1".equals(active)) {
            warehouseModel.setActive(Boolean.TRUE);
        } else if ("0".equals(active)) {
            warehouseModel.setActive(Boolean.FALSE);
        }
        //所属供货点
        warehouseModel.setPosId(warehouseCreateData.getPosId());
        warehouseModel.setVersion(0);
        warehouseService.createWarehouse(warehouseModel);
        WarehouseInfoResp warehouseInfoResp = new WarehouseInfoResp();
        convert(warehouseModel, warehouseInfoResp);
        return ResponseEntity.ok(warehouseInfoResp);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteWarehouse(@RequestParam(value = "id", required = true) String id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    private WarehouseInfoResp convert(WarehouseModel warehouseModel, WarehouseInfoResp warehouseInfoResp) {
        //主键
        warehouseInfoResp.setId(warehouseModel.getId());
        //仓库编码
        warehouseInfoResp.setCode(warehouseModel.getCode());
        //仓库名称
        warehouseInfoResp.setName(warehouseModel.getName());
        //是否启用
        Boolean active = warehouseModel.getActive();
        warehouseInfoResp.setActive(active != null && active ? "1" : "0");
        //所属供货点
        warehouseInfoResp.setPosId(warehouseModel.getPosId());
        //仓库地址
        return warehouseInfoResp;
    }

}
