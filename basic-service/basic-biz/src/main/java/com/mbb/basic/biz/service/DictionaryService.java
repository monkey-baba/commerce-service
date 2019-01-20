package com.mbb.basic.biz.service;

import com.mbb.basic.biz.dto.DictionaryInfoDto;
import com.mbb.basic.biz.dto.DictionaryInfoResponse;
import com.mbb.basic.biz.dto.DictionaryQueryDto;
import com.mbb.basic.biz.model.DictionaryModel;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface DictionaryService {

    /**
     * 查询枚举
     *
     * @param dictionaryQueryDto
     * @return
     */
    List<DictionaryInfoResponse> getDictionarys(DictionaryQueryDto dictionaryQueryDto);

    /**
     * 新增枚举
     *
     * @param dictionaryInfoDto
     */
    void addDictionary(DictionaryInfoDto dictionaryInfoDto);

    /**
     * 删除枚举
     *
     * @param id
     */
    void deleteDictionary(String id);


    List<DictionaryModel> findDictValues(String type);
}
