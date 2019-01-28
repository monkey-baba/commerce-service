package com.mbb.stock.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.stock.biz.dao.StoreMapper;
import com.mbb.stock.biz.dto.StoreListQuery;
import com.mbb.stock.biz.model.PointOfServiceModel;
import com.mbb.stock.biz.service.ReservoirAreaService;
import com.mbb.stock.common.dto.StoreInfoDto;
import com.mbb.stock.common.enumation.PosType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReservoirAreaServiceImpl implements ReservoirAreaService {
    private static final Integer limit = 25;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private IdService idService;


    @Override
    public List<PointOfServiceModel> getReservoirAreas(PointOfServiceModel storeModel) {
        Example example = mapQueryInfo(storeModel);
        List<PointOfServiceModel> reservoirAreaModels = storeMapper.selectByExample(example);
        return reservoirAreaModels;
    }

    @Override
    public void addReservoirArea(StoreInfoDto storeInfoDto) {
        if (storeInfoDto!=null) {
            PointOfServiceModel reservoirAreaModel = new PointOfServiceModel();
            reservoirAreaModel.setPosType(PosType.WAREHOUSE);
            reservoirAreaModel.setId(idService.genId());
            reservoirAreaModel.setCode(storeInfoDto.getCode());
            String name=storeInfoDto.getName();
            reservoirAreaModel.setName(StringUtils.isBlank(name) ? null : name);
            Long  classifyid=storeInfoDto.getClassification();
            reservoirAreaModel.setClassifyId(classifyid);
            String contact=storeInfoDto.getContact();
            reservoirAreaModel.setContact(StringUtils.isBlank(contact) ? null :contact);
            Long address = storeInfoDto.getAddress();
            reservoirAreaModel.setAddressId(address);
            Long status = storeInfoDto.getStatus();
            reservoirAreaModel.setStatusId(status);
            String owner = storeInfoDto.getOwner();
            reservoirAreaModel.setOwner(StringUtils.isBlank(owner) ? null : owner);
            storeMapper.insert(reservoirAreaModel);
        }
    }

    @Override
    public void updateReservoirArea(PointOfServiceModel var1) {
        int result = this.storeMapper.updateByPrimaryKey(var1);
    }

    @Override
    public PointOfServiceModel findById(Long id) {
        return (PointOfServiceModel)this.storeMapper.selectByPrimaryKey(id);
    }

    private List<StoreInfoDto> dealResult(List<PointOfServiceModel> storeModels) {
        List<StoreInfoDto> storeInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(storeModels)) {
            for (PointOfServiceModel storeModel : storeModels) {
                StoreInfoDto storeInfoDto = new StoreInfoDto();
                //大仓名称
                String name=storeModel.getName();
                storeInfoDto.setName(String.valueOf(name == null ? "" : name));
                //大仓地址
                Long address=storeModel.getAddressId();
                storeInfoDto.setAddress(address);
                //大仓状态
                Long status=storeModel.getAddressId();
                storeInfoDto.setStatus(status);
                //大仓负责人
                String owner=storeModel.getOwner();
                storeInfoDto.setOwner(String.valueOf(owner == null ? "" : owner));

                storeInfoDtoList.add(storeInfoDto);
            }
        }
        return storeInfoDtoList;
    }



    private Example mapQueryInfo(PointOfServiceModel storeModel) {
        //大仓名称
        String name = storeModel.getName();
        //大仓编码
        String code = storeModel.getCode();
        //大仓分类
        Long type = storeModel.getClassifyId();
        //大仓状态
        Long status = storeModel.getStatusId();
        //大仓负责人
        String owner = storeModel.getOwner();
        Example example = new Example(PointOfServiceModel.class);
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
        if(null!=type){
            criteria.andEqualTo("classifyId", type);
        }
        if(null!=status){
            criteria.andEqualTo("statusId", status);
        }
        criteria.andEqualTo("posType", PosType.WAREHOUSE);
        return example;
    }

}
