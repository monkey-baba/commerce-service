package com.mbb.basic.biz.service;

import com.mbb.basic.biz.model.DictionaryValueModel;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface DictionaryService {


    List<DictionaryValueModel> findDictValues(String type);
}
