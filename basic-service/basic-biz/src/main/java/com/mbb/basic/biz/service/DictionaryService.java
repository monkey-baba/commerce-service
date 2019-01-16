package com.mbb.basic.biz.service;


import com.mbb.basic.biz.model.DictionaryValueModel;
import java.util.List;

/**
 * 本地Service处理类
 */
public interface DictionaryService {

    List<DictionaryValueModel> findDictValues(String type);

}
