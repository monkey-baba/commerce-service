package com.mbb.basic.biz.service;

import com.mbb.basic.biz.dictionaryvalue.biz.model.DictionaryValueModel;
import com.mbb.basic.biz.dto.DictionaryInfoDto;
import com.mbb.basic.biz.dto.DictionaryInfoResponse;
import com.mbb.basic.biz.dto.DictionaryQueryDto;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:37
 */
public interface DictionaryValueService {

    DictionaryValueModel findById(Long id);
}
