package com.mbb.basic.biz.service.impl;

import com.mbb.basic.biz.dao.DictionaryMapper;
import com.mbb.basic.biz.dao.DictionaryValueMapper;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.model.DictionaryValueModel;
import com.mbb.basic.biz.service.DictionaryService;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Builder;
import tk.mybatis.mapper.util.Sqls;

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

    @Override
    public List<DictionaryModel> findDictByExample(DictionaryModel model) {
        final Example.Builder builder = Example.builder(DictionaryModel.class);
        final Sqls where = Sqls.custom();
        if (StringUtils.isNotEmpty(model.getName())) {
            where.andLike("name",  "%" + model.getName() + "%");
        }
        if (StringUtils.isNotEmpty(model.getCode())) {
            where.andLike("code", model.getCode() + "%");
        }
        final Example build = builder.where(where).build();
        return this.dictionaryMapper.selectByExample(build);

    }

    @Override
    public int createDict(DictionaryModel dict) {
        return dictionaryMapper.insert(dict);
    }


}
