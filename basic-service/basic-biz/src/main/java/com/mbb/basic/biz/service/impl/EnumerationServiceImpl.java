package com.mbb.basic.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.biz.dao.EnumerationMapper;
import com.mbb.basic.biz.dto.EnumerationInfoDto;
import com.mbb.basic.biz.dto.EnumerationQueryDto;
import com.mbb.enumeration.biz.model.EnumerationModel;
import com.mbb.basic.biz.service.EnumerationService;
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
 * @create 2019-01-14 18:01
 */
@Service
@Slf4j
public class EnumerationServiceImpl implements EnumerationService {

    private static final Logger logger = LogManager.getLogger(EnumerationServiceImpl.class);

    private static final Integer limit = 25;

    @Autowired
    private EnumerationMapper enumerationMapper;

    @Autowired
    private IdService idService;


    @Override
    public List<EnumerationInfoDto> getEnumerations(EnumerationQueryDto enumerationQueryDto) {
        //封装查询Example
        Example example = mapQueryInfo(enumerationQueryDto);
        //封装分页参数
        RowBounds rowBounds = mapRowBounds(enumerationQueryDto);
        List<EnumerationModel> enumerationModels = enumerationMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("enumeration size====" + enumerationModels.size());
        //处理返回结果
        return dealResult(enumerationModels);
    }

    @Override
    public void addEnumeration(List<EnumerationInfoDto> enumerationInfoDtoList) {
        if (!CollectionUtils.isEmpty(enumerationInfoDtoList)) {
            for (EnumerationInfoDto enumerationInfoDto : enumerationInfoDtoList) {
                EnumerationModel enumerationModel = new EnumerationModel();
                enumerationModel.setId(idService.genId());
                enumerationModel.setCode(enumerationInfoDto.getCode());
                enumerationModel.setName(enumerationInfoDto.getName());
                enumerationModel.setVersion(1);
                enumerationMapper.insert(enumerationModel);
            }
        }
    }

    @Override
    public void deleteEnumeration(String id) {
        enumerationMapper.deleteByPrimaryKey(id);
    }

    private List<EnumerationInfoDto> dealResult(List<EnumerationModel> enumerationModels) {
        List<EnumerationInfoDto> enumerationInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(enumerationModels)) {
            for (EnumerationModel enumerationModel : enumerationModels) {
                EnumerationInfoDto enumerationInfoDto = new EnumerationInfoDto();
                //ID
                enumerationInfoDto.setId(String.valueOf(enumerationModel.getId()));
                //编码
                enumerationInfoDto.setCode(enumerationModel.getCode());
                //客户名称
                enumerationInfoDto.setName(enumerationModel.getName());
                //乐观锁
                enumerationInfoDto.setVersion(enumerationModel.getVersion());

                enumerationInfoDtoList.add(enumerationInfoDto);
            }
        }
        return enumerationInfoDtoList;
    }

    private Example mapQueryInfo(EnumerationQueryDto enumerationQueryDto) {
        //编码
        String code = enumerationQueryDto.getCode();
        //名称
        String name = enumerationQueryDto.getName();
        Example example = new Example(EnumerationModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (StringUtils.isNotBlank(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        return example;
    }

    private RowBounds mapRowBounds(EnumerationQueryDto enumerationQueryDto) {
        String queryOffset = enumerationQueryDto.getOffset();
        Integer offset = StringUtils.isBlank(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }
}
