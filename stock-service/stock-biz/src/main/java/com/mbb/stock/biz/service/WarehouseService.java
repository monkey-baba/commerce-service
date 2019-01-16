package com.mbb.stock.biz.service;

import com.mbb.stock.biz.dto.WarehouseInfoDto;
import com.mbb.stock.biz.dto.WarehouseQueryDto;
import com.mbb.stock.biz.model.WarehouseModel;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-10 17:40
 */
public interface WarehouseService {

    /**
     * 仓库查询
     *
     * @param warehouseQueryDto
     * @return
     */
    List<WarehouseInfoDto> getWarehouses(WarehouseQueryDto warehouseQueryDto);

    /**
     * 新增仓库
     *
     * @param warehouseInfoDtoList
     */
    void addWarehouse(List<WarehouseInfoDto> warehouseInfoDtoList);

    /**
     * 删除仓库
     *
     * @param id
     */
    void deleteWarehouse(String id);
}
