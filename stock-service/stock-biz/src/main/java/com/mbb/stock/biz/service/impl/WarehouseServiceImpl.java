package com.mbb.stock.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.dao.WarehouseMapper;
import com.mbb.stock.biz.dto.StockInfoDto;
import com.mbb.stock.biz.dto.StockQueryDto;
import com.mbb.stock.biz.dto.WarehouseInfoDto;
import com.mbb.stock.biz.dto.WarehouseQueryDto;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.model.WarehouseModel;
import com.mbb.stock.biz.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

    private static final Integer limit = 25;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private IdService idService;

    @Override
    public List<WarehouseInfoDto> getWarehouses(WarehouseQueryDto warehouseQueryDto) {
        //封装查询Example
        Example example = mapQueryInfo(warehouseQueryDto);
        //封装分页参数
        RowBounds rowBounds = mapRowBounds(warehouseQueryDto);
        List<WarehouseModel> warehouseModels = warehouseMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("warehouse size====" + warehouseModels.size());
        //处理返回结果
        return dealResult(warehouseModels);
    }

    @Override
    public void addWarehouse(List<WarehouseInfoDto> warehouseInfoDtoList) {
        if (!CollectionUtils.isEmpty(warehouseInfoDtoList)) {
            for (WarehouseInfoDto warehouseInfoDto : warehouseInfoDtoList) {
                WarehouseModel warehouseModel = new WarehouseModel();
                warehouseModel.setId(idService.genId());
                //仓库编码
                warehouseModel.setCode(warehouseInfoDto.getCode());
                //仓库名称
                warehouseModel.setName(warehouseInfoDto.getName());
                warehouseModel.setVersion(1);
                //是否启用
                String active = warehouseInfoDto.getActive();
                if ("1".equals(active)) {
                    warehouseModel.setActive(Boolean.TRUE);
                } else if ("0".equals(active)) {
                    warehouseModel.setActive(Boolean.FALSE);
                }
                //所属供货点
                String posId = warehouseInfoDto.getPosId();
                warehouseModel.setPosId(StringUtils.isBlank(posId) ? null : Long.valueOf(posId));
                //仓库地址
                warehouseMapper.insert(warehouseModel);
            }
        }
    }

    @Override
    public void deleteWarehouse(String id) {
        warehouseMapper.deleteByPrimaryKey(id);
    }


    private List<WarehouseInfoDto> dealResult(List<WarehouseModel> warehouseModels) {
        List<WarehouseInfoDto> warehouseInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(warehouseModels)) {
            for (WarehouseModel warehouseModel : warehouseModels) {
                WarehouseInfoDto warehouseInfoDto = new WarehouseInfoDto();
                //主键
                warehouseInfoDto.setId(String.valueOf(warehouseModel.getId()));
                //仓库编码
                warehouseInfoDto.setCode(warehouseModel.getCode());
                //仓库名称
                warehouseInfoDto.setName(warehouseModel.getName());
                //是否启用
                Boolean active = warehouseModel.getActive();
                warehouseInfoDto.setActive(active != null && active ? "1" : "0");
                //所属供货点
                Long posId = warehouseModel.getPosId();
                warehouseInfoDto.setPosId(posId == null ? "" : String.valueOf(posId));
                //仓库地址
                warehouseInfoDtoList.add(warehouseInfoDto);
            }
        }
        return warehouseInfoDtoList;
    }

    private Example mapQueryInfo(WarehouseQueryDto warehouseQueryDto) {
        //仓库编码
        String code = warehouseQueryDto.getCode();
        //仓库名称
        String name = warehouseQueryDto.getName();
        Example example = new Example(StockModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        return example;
    }

    private RowBounds mapRowBounds(WarehouseQueryDto warehouseQueryDto) {
        String queryOffset = warehouseQueryDto.getOffset();
        Integer offset = StringUtils.isBlank(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }
}
