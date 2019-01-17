package com.mbb.dictionaryvalue.biz.service;
import com.mbb.dictionaryvalue.biz.dto.DictionaryValueInfoDto;
import com.mbb.dictionaryvalue.biz.dto.DictionaryValueQueryDto;
import com.mbb.dictionaryvalue.biz.dto.DictionaryValueResponse;

import java.util.List;

;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface DictionaryValueService {

    /**
     * 查询枚举
     *
     * @param dictionaryValueQueryDto
     * @return
     */
    List<DictionaryValueResponse> getDictionaryValues(DictionaryValueQueryDto dictionaryValueQueryDto);

    /**
     * 新增枚举
     *
     * @param dictionaryValueInfoDtoList
     */
    void addDictionaryValue(List<DictionaryValueInfoDto> dictionaryValueInfoDtoList);

    /**
     * 删除枚举
     *
     * @param id
     */
    void deleteDictionaryValue(String id);
}
