package com.mbb.stock.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.common.dto.AddressData;
import com.mbb.stock.adapter.PosAddressAdapter;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.model.WarehouseModel;
import com.mbb.stock.biz.service.StoreService;
import com.mbb.stock.biz.service.WarehouseService;
import com.mbb.stock.rest.dto.*;
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
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private IdService idService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private PosAddressAdapter posAddressAdapter;

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
        warehouseModel.setActive(warehouseCreateData.getEnabled());
        //所属供货点
        warehouseModel.setPosId(warehouseCreateData.getPosId());
        warehouseModel.setVersion(0);
        warehouseService.createWarehouse(warehouseModel);
        WarehouseInfoResp warehouseInfoResp = new WarehouseInfoResp();
        convert(warehouseModel, warehouseInfoResp);
        return ResponseEntity.ok(warehouseInfoResp);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteWarehouse(@RequestBody List<WarehouseDeleteData> datas) {
        for (WarehouseDeleteData data : datas) {
            warehouseService.deleteWarehouse(data.getCode());
        }
        return ResponseEntity.ok("删除成功");
    }

    @PostMapping("/enable")
    public ResponseEntity enableWarehouse(@RequestBody List<WarehouseEnableData> datas) {
        for (WarehouseEnableData data : datas) {
            warehouseService.enableWarehouse(data.getCode(), data.getEnabled());
        }
        return ResponseEntity.ok("更新成功");
    }

    @PostMapping("/update")
    public ResponseEntity updateWarehouse(@RequestBody WarehouseUpdateData data) {
        WarehouseModel warehouseModel = warehouseService.findById(data.getId());
        warehouseModel.setId(data.getId());
        warehouseModel.setCode(data.getCode());
        warehouseModel.setName(data.getName());
        warehouseService.updateWarehouse(warehouseModel);
        return ResponseEntity.ok("更新成功");
    }

    private WarehouseInfoResp convert(WarehouseModel warehouseModel, WarehouseInfoResp warehouseInfoResp) {
        //主键
        warehouseInfoResp.setId(warehouseModel.getId());
        //仓库编码
        warehouseInfoResp.setCode(warehouseModel.getCode());
        //仓库名称
        warehouseInfoResp.setName(warehouseModel.getName());
        //是否启用
        warehouseInfoResp.setEnabled(warehouseModel.getActive());
        //所属供货点
        Long posId = warehouseModel.getPosId();
        if (posId != null) {
            PointOfServiceModel pointOfServiceModel = storeService.findPosById(posId);
            warehouseInfoResp.setPosId(posId);
            warehouseInfoResp.setPosName(pointOfServiceModel.getName());
            Long addressId = pointOfServiceModel.getAddressId();
            if (addressId != null) {
                AddressData address = posAddressAdapter.getAddress(addressId);
                warehouseInfoResp.setPosAddress(address.getDetail());
            }
        }
        //仓库地址
        return warehouseInfoResp;
    }

}
