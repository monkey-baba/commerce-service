package com.mbb.stock.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.dao.StockMapper;
import com.mbb.stock.biz.dao.StoreMapper;
import com.mbb.stock.biz.dto.StoreInfoDto;
import com.mbb.stock.biz.dto.StoreListQuery;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.model.StockModel;
import com.mbb.stock.biz.service.StoreService;
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
@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = LogManager.getLogger(StockServiceImpl.class);

    private static final Integer limit = 25;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private IdService idService;

    @Override
    public List<StoreInfoDto> getStores(StoreListQuery storeListQuery) {
        //封装查询Example
        Example example = mapQueryInfo(storeListQuery);
        //封装分页参数
        RowBounds rowBounds = mapRowBounds(storeListQuery);
        List<PointOfServiceModel> storeModels = storeMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("stock size====" + storeModels.size());
        //处理返回结果
        return dealResult(storeModels);
    }

    @Override
    public List<StoreInfoDto> getAllStores() {
        List<PointOfServiceModel> storeModels = storeMapper.selectAll();
        logger.info("stock size====" + storeModels.size());
        //处理返回结果
        return dealResult(storeModels);
    }

    @Override
    public void addStore(List<StoreInfoDto> storeInfoDtoList) {
        if (!CollectionUtils.isEmpty(storeInfoDtoList)) {
            for (StoreInfoDto storeInfoDto : storeInfoDtoList) {
                PointOfServiceModel storeModel = new PointOfServiceModel();
                storeModel.setId(idService.genId());
                storeModel.setCode(storeInfoDto.getCode());
                String name=storeInfoDto.getName();
                storeModel.setName(StringUtils.isBlank(name) ? null : name);
                String  classifyid=storeInfoDto.getClassifyid();
                storeModel.setClassifyId(StringUtils.isBlank(classifyid) ? null : Long.valueOf(classifyid));
                String contact=storeInfoDto.getContact();
                storeModel.setContact(StringUtils.isBlank(contact) ? null :contact);
                String address = storeInfoDto.getAddress();
                storeModel.setAddressId(StringUtils.isBlank(address) ? null : Long.valueOf(address));
                String status = storeInfoDto.getStatus();
                storeModel.setStatusId(StringUtils.isBlank(status) ? null : Long.valueOf(status));
                String owner = storeInfoDto.getOwner();
                storeModel.setOwner(StringUtils.isBlank(owner) ? null : owner);
                storeMapper.insert(storeModel);
            }
        }
    }

    private List<StoreInfoDto> dealResult(List<PointOfServiceModel> storeModels) {
        List<StoreInfoDto> storeInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(storeModels)) {
            for (PointOfServiceModel storeModel : storeModels) {
                StoreInfoDto storeInfoDto = new StoreInfoDto();
                //门店名称
                String name=storeModel.getName();
                storeInfoDto.setName(String.valueOf(name == null ? "" : name));
                //门店地址
                Long address=storeModel.getAddressId();
                storeInfoDto.setAddress(String.valueOf(address == null ? "" : String.valueOf(address)));
                //门店状态
                Long status=storeModel.getAddressId();
                storeInfoDto.setStatus(String.valueOf(status == null ? "" : String.valueOf(status)));
                //门店负责人
                String owner=storeModel.getOwner();
                storeInfoDto.setOwner(String.valueOf(owner == null ? "" : owner));

                storeInfoDtoList.add(storeInfoDto);
            }
        }
        return storeInfoDtoList;
    }



    private Example mapQueryInfo(StoreListQuery storeListQuery) {
        //门店名称
        String name = storeListQuery.getName();
        //门店编码
        String code = storeListQuery.getCode();
        //门店分类
        String type = storeListQuery.getType();
        //门店状态
        String status = storeListQuery.getStatus();
        //门店负责人
        String owner = storeListQuery.getOwner();

        Example example = new Example(StockModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }

        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(owner)) {
            criteria.andLike("owner", "%" + owner + "%");
        }
        return example;
    }
    private RowBounds mapRowBounds(StoreListQuery storeListQuery) {
        String queryOffset = storeListQuery.getOffset();
        Integer offset = StringUtils.isBlank(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }
}
