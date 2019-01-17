package com.mbb.stock.biz.service;

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
     * @param warehouseModel
     * @return
     */
    List<WarehouseModel> getWarehouses(WarehouseModel warehouseModel);

    /**
     * 新增仓库
     *
     * @param warehouseModel
     */
    void createWarehouse(WarehouseModel warehouseModel);

    /**
     * 删除仓库
     *
     * @param id
     */
    void deleteWarehouse(String id);

}
