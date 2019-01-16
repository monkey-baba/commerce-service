package com.mbb.enumeration.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.biz.dao.EnumerationMapper;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.enumeration.biz.dto.EnumerationInfoDto;
import com.mbb.enumeration.biz.dto.EnumerationQueryDto;
import com.mbb.enumeration.biz.model.DictionaryModel;
import com.mbb.enumeration.biz.service.EnumerationService;
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
        List<DictionaryModel> dictionaryModels = enumerationMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("enumeration size====" + dictionaryModels.size());
        //处理返回结果
        return dealResult(dictionaryModels);
    }

    @Override
    public void addEnumeration(List<EnumerationInfoDto> enumerationInfoDtoList) {
        if (!CollectionUtils.isEmpty(enumerationInfoDtoList)) {
            for (EnumerationInfoDto enumerationInfoDto : enumerationInfoDtoList) {
                DictionaryModel dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(idService.genId());
                dictionaryModel.setCode(enumerationInfoDto.getCode());
                dictionaryModel.setName(enumerationInfoDto.getName());
                dictionaryModel.setVersion(1);
                enumerationMapper.insert(dictionaryModel);
            }
        }
    }

    @Override
    public void deleteEnumeration(String id) {
        enumerationMapper.deleteByPrimaryKey(id);
    }

    private List<EnumerationInfoDto> dealResult(List<DictionaryModel> dictionaryModels) {
        List<EnumerationInfoDto> enumerationInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dictionaryModels)) {
            for (DictionaryModel dictionaryModel : dictionaryModels) {
                EnumerationInfoDto enumerationInfoDto = new EnumerationInfoDto();
                //ID
                enumerationInfoDto.setId(String.valueOf(dictionaryModel.getId()));
                //编码
                enumerationInfoDto.setCode(dictionaryModel.getCode());
                //客户名称
                enumerationInfoDto.setName(dictionaryModel.getName());
                //乐观锁
                enumerationInfoDto.setVersion(dictionaryModel.getVersion());

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
        Example example = new Example(DictionaryModel.class);
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
