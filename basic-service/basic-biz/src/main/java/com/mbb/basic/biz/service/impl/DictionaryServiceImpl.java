package com.mbb.basic.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.biz.dao.DictionaryMapper;
import com.mbb.basic.biz.dto.DictionaryInfoDto;
import com.mbb.basic.biz.dto.DictionaryQueryDto;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.service.DictionaryService;
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
public class DictionaryServiceImpl implements DictionaryService {

    private static final Logger logger = LogManager.getLogger(DictionaryServiceImpl.class);

    private static final Integer limit = 25;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private IdService idService;


    @Override
    public List<DictionaryInfoDto> getDictionarys(DictionaryQueryDto dictionaryQueryDto) {
        //封装查询Example
        Example example = mapQueryInfo(dictionaryQueryDto);
        //封装分页参数
        RowBounds rowBounds = mapRowBounds(dictionaryQueryDto);
        List<DictionaryModel> dictionaryModels = dictionaryMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("dictionary size====" + dictionaryModels.size());
        //处理返回结果
        return dealResult(dictionaryModels);
    }

    @Override
    public void addDictionary(List<DictionaryInfoDto> dictionaryInfoDtoList) {
        if (!CollectionUtils.isEmpty(dictionaryInfoDtoList)) {
            for (DictionaryInfoDto dictionaryInfoDto : dictionaryInfoDtoList) {
                DictionaryModel dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(idService.genId());
                dictionaryModel.setCode(dictionaryInfoDto.getCode());
                dictionaryModel.setName(dictionaryInfoDto.getName());
                dictionaryModel.setVersion(1);
                dictionaryMapper.insert(dictionaryModel);
            }
        }
    }

    @Override
    public void deleteDictionary(String id) {
        dictionaryMapper.deleteByPrimaryKey(id);
    }

    private List<DictionaryInfoDto> dealResult(List<DictionaryModel> dictionaryModels) {
        List<DictionaryInfoDto> dictionaryInfoDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dictionaryModels)) {
            for (DictionaryModel dictionaryModel : dictionaryModels) {
                DictionaryInfoDto dictionaryInfoDto = new DictionaryInfoDto();
                //ID
                dictionaryInfoDto.setId(String.valueOf(dictionaryModel.getId()));
                //编码
                dictionaryInfoDto.setCode(dictionaryModel.getCode());
                //客户名称
                dictionaryInfoDto.setName(dictionaryModel.getName());
                //乐观锁
                if(dictionaryModel.getVersion()!=null){
                    dictionaryInfoDto.setVersion(dictionaryModel.getVersion().toString());
                }

                dictionaryInfoDtoList.add(dictionaryInfoDto);
            }
        }
        return dictionaryInfoDtoList;
    }

    private Example mapQueryInfo(DictionaryQueryDto dictionaryQueryDto) {
        //编码
        String code = dictionaryQueryDto.getCode();
        //名称
        String name = dictionaryQueryDto.getName();
        Example example = new Example(DictionaryModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        return example;
    }

    private RowBounds mapRowBounds(DictionaryQueryDto dictionaryQueryDto) {
        String queryOffset = dictionaryQueryDto.getOffset();
        Integer offset = StringUtils.isEmpty(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }
}
