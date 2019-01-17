package com.mbb.dictionaryvalue.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.dictionaryValuevalue.biz.model.DictionaryValueModel;
import com.mbb.dictionaryvalue.biz.dao.DictionaryValueMapper;
import com.mbb.dictionaryvalue.biz.dto.DictionaryValueInfoDto;
import com.mbb.dictionaryvalue.biz.dto.DictionaryValueQueryDto;
import com.mbb.dictionaryvalue.biz.dto.DictionaryValueResponse;
import com.mbb.dictionaryvalue.biz.service.DictionaryValueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
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
public class DictionaryValueServiceImpl implements DictionaryValueService {

    private static final Logger logger = LogManager.getLogger(DictionaryValueServiceImpl.class);

    private static final Integer limit = 25;

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    @Autowired
    private IdService idService;


    @Override
    public List<DictionaryValueInfoDto> getDictionaryValues(DictionaryValueQueryDto dictionaryValueQueryDto) {
        //封装查询Example
        Example example = mapQueryInfo(dictionaryValueQueryDto);
        //封装分页参数
        RowBounds rowBounds = mapRowBounds(dictionaryValueQueryDto);
        List<DictionaryValueModel> dictionaryValueModels = dictionaryValueMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("dictionaryValue size====" + dictionaryValueModels.size());
        //处理返回结果
        return dealResult(dictionaryValueModels);
    }

    @Override
    public void addDictionaryValue(List<com.mbb.dictionaryvalue.biz.dto.DictionaryValueInfoDto> dictionaryValueInfoDtoList) {
        if (!CollectionUtils.isEmpty(dictionaryValueInfoDtoList)) {
            for (DictionaryValueInfoDto dictionaryValueInfoDto : dictionaryValueInfoDtoList) {
                DictionaryValueModel dictionaryValueModel = new DictionaryValueModel();
                dictionaryValueModel.setId(idService.genId());
                dictionaryValueModel.setCode(dictionaryValueInfoDto.getCode());
                dictionaryValueModel.setName(dictionaryValueInfoDto.getName());
                dictionaryValueModel.setVersion(1);
                dictionaryValueMapper.insert(dictionaryValueModel);
            }
        }
    }

    @Override
    public void deleteDictionaryValue(String id) {
        dictionaryValueMapper.deleteByPrimaryKey(id);
    }

    private List<DictionaryValueResponse> dealResult(List<DictionaryValueModel> dictionaryValueModels) {
        List<DictionaryValueResponse> dictionaryValueInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dictionaryValueModels)) {
            for (DictionaryValueModel dictionaryValueModel : dictionaryValueModels) {
                DictionaryValueResponse dictionaryValueInfoDto = new DictionaryValueResponse();
                //ID
                dictionaryValueInfoDto.setId(String.valueOf(dictionaryValueModel.getId()));
                //乐观锁
                if(dictionaryValueModel.getVersion()!=null){
                    dictionaryValueInfoDto.setVersion(dictionaryValueModel.getVersion().toString());
                }
                //值
                dictionaryValueInfoDto.setCode(dictionaryValueModel.getCode());
                //含义
                dictionaryValueInfoDto.setName(dictionaryValueModel.getName());
                //描述
                dictionaryValueInfoDto.setDescription(dictionaryValueModel.getDescription());
                //排序号
                if(dictionaryValueModel.getTypeId()!=null) {
                    dictionaryValueInfoDto.setType_id(dictionaryValueModel.getTypeId().toString());
                }
                //预留字段1
                dictionaryValueInfoDto.setAttribute1(dictionaryValueModel.getAttribute1());
                //预留字段2
                dictionaryValueInfoDto.setAttribute2(dictionaryValueModel.getAttribute2());
                //预留字段3
                dictionaryValueInfoDto.setAttribute3(dictionaryValueModel.getAttribute3());
                //是否启用
                dictionaryValueInfoDto.setActive(dictionaryValueModel.getActive());

                dictionaryValueInfoDtoList.add(dictionaryValueInfoDto);
            }
        }
        return dictionaryValueInfoDtoList;
    }

    private Example mapQueryInfo(DictionaryValueQueryDto dictionaryValueQueryDto) {
        //编码
        String code = dictionaryValueQueryDto.getCode();
        Example example = new Example(DictionaryValueModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        return example;
    }

    private RowBounds mapRowBounds(DictionaryValueQueryDto dictionaryValueQueryDto) {
        String queryOffset = dictionaryValueQueryDto.getOffset();
        Integer offset = StringUtils.isEmpty(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }
}
