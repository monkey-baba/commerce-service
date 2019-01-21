package com.mbb.basic.biz.service.impl;

import com.mbb.basic.biz.dao.DictionaryValueMapper;
import com.mbb.basic.biz.model.DictionaryValueModel;
import com.mbb.basic.biz.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryValueServiceImpl implements DictionaryValueService {

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    @Override
    public DictionaryValueModel findById(Long id) {
        return dictionaryValueMapper.selectByPrimaryKey(id);
    }
}
