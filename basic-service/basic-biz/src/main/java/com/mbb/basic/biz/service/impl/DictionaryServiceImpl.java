package com.mbb.basic.biz.service.impl;

import com.github.pagehelper.PageInfo;
import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.biz.dao.DictionaryMapper;
import com.mbb.basic.biz.dao.DictionaryValueMapper;
import com.mbb.basic.biz.dto.DictionaryInfoDto;
import com.mbb.basic.biz.dto.DictionaryInfoResponse;
import com.mbb.basic.biz.dto.DictionaryQueryDto;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.model.DictionaryValueModel;
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
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    @Override
    public List<DictionaryValueModel> findDictValues(String type) {
        Builder dict = Example.builder(DictionaryModel.class);
        dict.where(Sqls.custom().andEqualTo("code",type));
        DictionaryModel dictModel = dictionaryMapper.selectOneByExample(dict.build());
        if (dictModel == null){
            return Collections.emptyList();
        }
        Builder values = Example.builder(DictionaryValueModel.class);
        values.where(Sqls.custom().andEqualTo("typeId",dictModel.getId()));
        return dictionaryValueMapper.selectByExample(values.build());
    }




}
