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
     * 根据id查找仓库
     *
     * @param id
     * @return
     */
    WarehouseModel findById(Long id);

    /**
     * 根据编号查找仓库
     *
     * @param code
     * @return
     */
    WarehouseModel findByCode(String code);

    /**
     * 获取所有仓库列表
     *
     * @return
     */
    List<WarehouseModel> findAllWarehouses();

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
     * @param code
     */
    void deleteWarehouse(String code);

    /**
     * 是否启用
     *
     * @param code
     * @param enabled
     */
    void enableWarehouse(String code, Boolean enabled);

    /**
     * 更新仓库
     *
     * @param warehouseModel
     */
    void updateWarehouse(WarehouseModel warehouseModel);

}
