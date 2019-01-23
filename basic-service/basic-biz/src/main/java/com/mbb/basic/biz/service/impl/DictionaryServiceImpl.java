package com.mbb.basic.biz.service.impl;

import com.mbb.basic.biz.dao.DictionaryMapper;
import com.mbb.basic.biz.dao.DictionaryValueMapper;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.model.DictionaryValueModel;
import com.mbb.basic.biz.service.DictionaryService;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public List<DictionaryValueModel> findDictValues(String type, Boolean active) {
        Builder dict = Example.builder(DictionaryModel.class);
        dict.where(Sqls.custom().andEqualTo("code", type));

        DictionaryModel dictModel = dictionaryMapper.selectOneByExample(dict.build());
        if (dictModel == null) {
            return Collections.emptyList();
        }
        Builder values = Example.builder(DictionaryValueModel.class);
        Sqls custom = Sqls.custom();
        values.where(custom.andEqualTo("typeId", dictModel.getId()));
        if (active != null) {
            custom.andEqualTo("active", active);
        }
        return dictionaryValueMapper.selectByExample(values.build());
    }

    @Override
    public List<DictionaryModel> findDictByExample(DictionaryModel model) {
        final Example.Builder builder = Example.builder(DictionaryModel.class);
        final Sqls where = Sqls.custom();
        if (StringUtils.isNotEmpty(model.getName())) {
            where.andLike("name", "%" + model.getName() + "%");
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictAndValues(DictionaryModel dict, List<DictionaryValueModel> add,
            List<DictionaryValueModel> update, List<DictionaryValueModel> delete) {
        dictionaryMapper.updateByPrimaryKey(dict);
        //处理新增
        if (CollectionUtils.isNotEmpty(add)){
            dictionaryValueMapper.insertList(add);
        }
        //处理更新
        update.forEach(v-> dictionaryValueMapper.updateByPrimaryKeySelective(v));
        //处理删除
        delete.forEach(v-> dictionaryValueMapper.delete(v));

    }

    @Override
    public DictionaryModel findDictById(Long id) {
        return dictionaryMapper.selectByPrimaryKey(id);
    }


}
