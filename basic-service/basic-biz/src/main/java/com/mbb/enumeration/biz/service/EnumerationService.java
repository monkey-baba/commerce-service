package com.mbb.enumeration.biz.service;

import com.mbb.enumeration.biz.dto.EnumerationInfoDto;
import com.mbb.enumeration.biz.dto.EnumerationQueryDto;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface EnumerationService {

    /**
     * 查询客户
     *
     * @param enumerationQueryDto
     * @return
     */
    List<EnumerationInfoDto> getEnumerations(EnumerationQueryDto enumerationQueryDto);

    /**
     * 新增客户
     *
     * @param enumerationInfoDtoList
     */
    void addEnumeration(List<EnumerationInfoDto> enumerationInfoDtoList);

    /**
     * 删除客户
     *
     * @param id
     */
    void deleteEnumeration(String id);
}
