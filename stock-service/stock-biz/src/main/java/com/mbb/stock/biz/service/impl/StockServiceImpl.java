package com.mbb.stock.biz.service.impl;


import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.dao.StockMapper;
import com.mbb.stock.biz.dto.StockInfoDto;
import com.mbb.stock.biz.dto.StockQueryDto;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.service.StockService;
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
 * @create 2019-01-08 15:38
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    private static final Logger logger = LogManager.getLogger(StockServiceImpl.class);

    private static final Integer limit = 25;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private IdService idService;

    @Override
    public List<StockInfoDto> getAllStocks() {
        List<StockModel> stockModels = stockMapper.selectAll();
        logger.info("stock size====" + stockModels.size());
        //处理返回结果
        return dealResult(stockModels);
    }

    @Override
    public List<StockInfoDto> getStocks(StockQueryDto stockQueryDto) {
        //封装查询Example
        Example example = mapQueryInfo(stockQueryDto);
        //封装分页参数
        RowBounds rowBounds = mapRowBounds(stockQueryDto);
        List<StockModel> stockModels = stockMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("stock size====" + stockModels.size());
        //处理返回结果
        return dealResult(stockModels);
    }

    @Override
    public void addStock(List<StockInfoDto> stockInfoDtoList) {
        if (!CollectionUtils.isEmpty(stockInfoDtoList)) {
            for (StockInfoDto stockInfoDto : stockInfoDtoList) {
                StockModel stockModel = new StockModel();
                stockModel.setId(idService.genId());
                String skuId = stockInfoDto.getSkuId();
                stockModel.setSkuId(StringUtils.isBlank(skuId) ? null : Long.valueOf(skuId));
                String warehouseId = stockInfoDto.getWarehouseId();
                stockModel.setWarehouseId(StringUtils.isBlank(warehouseId) ? null : Long.valueOf(warehouseId));
                String available = stockInfoDto.getAvailable();
                stockModel.setAvailable(StringUtils.isBlank(available) ? null : Long.valueOf(available));
                stockModel.setVersion(1);
                stockMapper.insert(stockModel);
            }
        }
    }

    @Override
    public void deleteStock(String id) {
        stockMapper.deleteByPrimaryKey(id);
    }

    private List<StockInfoDto> dealResult(List<StockModel> stockModels) {
        List<StockInfoDto> stockInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(stockModels)) {
            for (StockModel stockModel : stockModels) {
                StockInfoDto stockInfoDto = new StockInfoDto();
                //主键
                stockInfoDto.setId(String.valueOf(stockModel.getId()));
                //商品编码
                Long skuId = stockModel.getSkuId();
                stockInfoDto.setSkuId(skuId == null ? "" : String.valueOf(skuId));
                //商品名称
//                stockInfoDto.setSkuName();
                //仓库编码
                Long warehouseId = stockModel.getWarehouseId();
                stockInfoDto.setWarehouseId(warehouseId == null ? "" : String.valueOf(warehouseId));
                //可用量
                Long available = stockModel.getAvailable();
                stockInfoDto.setAvailable(available == null ? "0" : String.valueOf(available));
                stockInfoDtoList.add(stockInfoDto);
            }
        }
        return stockInfoDtoList;
    }

    private Example mapQueryInfo(StockQueryDto stockQueryDto) {
        //商品编码
        String skuId = stockQueryDto.getSkuId();
        //商品名称
        String skuName = stockQueryDto.getSkuName();
        //仓库编码
        String warehouseId = stockQueryDto.getWarehouseId();
        //仓库名称
        String warehouseName = stockQueryDto.getWarehouseName();
        Example example = new Example(StockModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(skuId)) {
            criteria.andLike("skuId", "%" + skuId + "%");
        }
        if (StringUtils.isNotBlank(warehouseId)) {
            criteria.andLike("warehouseId", "%" + warehouseId + "%");
        }
        return example;
    }

    private RowBounds mapRowBounds(StockQueryDto stockQueryDto) {
        String queryOffset = stockQueryDto.getOffset();
        Integer offset = StringUtils.isBlank(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }

}
