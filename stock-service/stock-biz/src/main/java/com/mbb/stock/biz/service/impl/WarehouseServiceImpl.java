package com.mbb.stock.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.dao.WarehouseMapper;
import com.mbb.stock.biz.model.WarehouseModel;
import com.mbb.stock.biz.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(WarehouseServiceImpl.class);

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private IdService idService;

    @Override
    public List<WarehouseModel> getWarehouses(WarehouseModel warehouseModel) {
        //封装查询Example
        Example example = mapQueryInfo(warehouseModel);
        List<WarehouseModel> warehouseModels = warehouseMapper.selectByExample(example);
        logger.info("warehouse size====" + warehouseModels.size());
        return warehouseModels;
    }

    @Override
    public void createWarehouse(WarehouseModel warehouseModel) {
        if (warehouseModel != null) {
            warehouseMapper.insert(warehouseModel);
        }
    }

    @Override
    public void deleteWarehouse(String id) {
        warehouseMapper.deleteByPrimaryKey(id);
    }

    private Example mapQueryInfo(WarehouseModel warehouseModel) {
        //仓库编码
        String code = warehouseModel.getCode();
        //仓库名称
        String name = warehouseModel.getName();
        Example example = new Example(WarehouseModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        return example;
    }

}
