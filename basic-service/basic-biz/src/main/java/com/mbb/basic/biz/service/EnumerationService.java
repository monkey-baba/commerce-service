package com.mbb.basic.biz.service;

import com.mbb.basic.biz.dto.EnumerationInfoDto;
import com.mbb.basic.biz.dto.EnumerationQueryDto;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface EnumerationService {

    /**
     * 查询枚举
     *
     * @param enumerationQueryDto
     * @return
     */
    List<EnumerationInfoDto> getEnumerations(EnumerationQueryDto enumerationQueryDto);

    /**
     * 新增枚举
     *
     * @param enumerationInfoDtoList
     */
    void addEnumeration(List<EnumerationInfoDto> enumerationInfoDtoList);

    /**
     * 删除枚举
     *
     * @param id
     */
    void deleteEnumeration(String id);
}
