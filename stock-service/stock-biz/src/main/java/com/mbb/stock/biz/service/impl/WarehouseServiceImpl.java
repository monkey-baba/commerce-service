package com.mbb.stock.biz.service.impl;

import com.mbb.stock.biz.dao.WarehouseMapper;
import com.mbb.stock.biz.model.WarehouseModel;
import com.mbb.stock.biz.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-10 17:40
 */
@Service
@Slf4j
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public WarehouseModel findById(Long id) {
        return warehouseMapper.selectByPrimaryKey(id);
    }

    @Override
    public WarehouseModel findByCode(String code) {
        Example example = new Example(WarehouseModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        return warehouseMapper.selectOneByExample(example);
    }

    @Override
    public List<WarehouseModel> findAllWarehouses() {
        return warehouseMapper.selectAll();
    }

    @Override
    public List<WarehouseModel> getWarehouses(WarehouseModel warehouseModel) {
        //封装查询Example
        Example example = mapQueryInfo(warehouseModel);
        List<WarehouseModel> warehouseModels = warehouseMapper.selectByExample(example);
        log.info("warehouse size====" + warehouseModels.size());
        return warehouseModels;
    }

    @Override
    public void createWarehouse(WarehouseModel warehouseModel) {
        if (warehouseModel != null) {
            warehouseMapper.insert(warehouseModel);
        }
    }

    @Override
    public void deleteWarehouse(String code) {
        log.info("code==" + code);
        Example example = new Example(WarehouseModel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("code", code);
        WarehouseModel warehouseModel = warehouseMapper.selectOneByExample(example);
        warehouseMapper.deleteByPrimaryKey(warehouseModel);
    }

    @Override
    public void enableWarehouse(String code, Boolean enabled) {
        WarehouseModel warehouseModel = this.findByCode(code);
        warehouseModel.setActive(enabled);
        this.warehouseMapper.updateByPrimaryKey(warehouseModel);
    }

    @Override
    public void updateWarehouse(WarehouseModel warehouseModel) {
        this.warehouseMapper.updateByPrimaryKey(warehouseModel);
    }

    private Example mapQueryInfo(WarehouseModel warehouseModel) {
        //仓库编码
        String code = warehouseModel.getCode();
        //仓库名称
        String name = warehouseModel.getName();
        Example example = new Example(WarehouseModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", name + "%");
        }
        return example;
    }

}
