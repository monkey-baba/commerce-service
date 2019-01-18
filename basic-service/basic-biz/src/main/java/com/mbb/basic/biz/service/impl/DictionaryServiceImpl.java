package com.mbb.basic.biz.service.impl;

import com.lxm.idgenerator.service.intf.IdService;
import com.mbb.basic.biz.dao.DictionaryMapper;
import com.mbb.basic.biz.dto.DictionaryInfoDto;
import com.mbb.basic.biz.dto.DictionaryInfoResponse;
import com.mbb.basic.biz.dto.DictionaryQueryDto;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.service.DictionaryService;
import com.mbb.dictionaryvalue.biz.model.DictionaryValueModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-14 18:01
 */
@Service
@Slf4j
public class DictionaryServiceImpl implements DictionaryService {

    private static final Logger logger = LogManager.getLogger(DictionaryServiceImpl.class);

    private static final Integer limit = 25;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private IdService idService;


    @Override
    public List<DictionaryInfoResponse> getDictionarys(DictionaryQueryDto dictionaryQueryDto) {
        //��װ��ѯExample
        Example example = mapQueryInfo(dictionaryQueryDto);
        //��װ��ҳ����
        RowBounds rowBounds = mapRowBounds(dictionaryQueryDto);
        List<DictionaryModel> dictionaryModels = dictionaryMapper.selectByExampleAndRowBounds(example, rowBounds);
        logger.info("dictionary size====" + dictionaryModels.size());
        //�����ؽ��
        return dealResult(dictionaryModels);
    }

    @Override
    public void addDictionary(List<DictionaryInfoDto> dictionaryResponseList) {
        if (!CollectionUtils.isEmpty(dictionaryResponseList)) {
            for (DictionaryInfoDto dictionaryResponse : dictionaryResponseList) {
                DictionaryModel dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(idService.genId());
                dictionaryModel.setCode(dictionaryResponse.getCode());
                dictionaryModel.setName(dictionaryResponse.getName());
                dictionaryModel.setVersion(1);
                dictionaryMapper.insert(dictionaryModel);
            }
        }
    }

    @Override
    public void deleteDictionary(String id) {
        dictionaryMapper.deleteByPrimaryKey(id);
    }

    private List<DictionaryInfoResponse> dealResult(List<DictionaryModel> dictionaryModels) {
        List<DictionaryInfoResponse> dictionaryResponseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(dictionaryModels)) {
            for (DictionaryModel dictionaryModel : dictionaryModels) {
                DictionaryInfoResponse dictionaryResponse = new DictionaryInfoResponse();
                //ID
                dictionaryResponse.setId(String.valueOf(dictionaryModel.getId()));
                //����
                dictionaryResponse.setCode(dictionaryModel.getCode());
                //�ͻ�����
                dictionaryResponse.setName(dictionaryModel.getName());
                //�ֹ���
                if(dictionaryModel.getVersion()!=null){
                    dictionaryResponse.setVersion(dictionaryModel.getVersion().toString());
                }

                dictionaryResponseList.add(dictionaryResponse);
            }
        }
        return dictionaryResponseList;
    }

    private Example mapQueryInfo(DictionaryQueryDto dictionaryQueryDto) {
        //����
        String code = dictionaryQueryDto.getCode();
        //����
        String name = dictionaryQueryDto.getName();
        Example example = new Example(DictionaryModel.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(code)) {
            criteria.andLike("code", "%" + code + "%");
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        return example;
    }

    private RowBounds mapRowBounds(DictionaryQueryDto dictionaryQueryDto) {
        String queryOffset = dictionaryQueryDto.getOffset();
        Integer offset = StringUtils.isEmpty(queryOffset) ? 0 : Integer.valueOf(queryOffset);
        RowBounds rowBounds = new RowBounds(offset, limit);
        return rowBounds;
    }

    @Override
    public List<DictionaryValueModel> findDictValues(String type) {
        Example.Builder dict = Example.builder(DictionaryModel.class);
        dict.where(Sqls.custom().andEqualTo("code",type));
        DictionaryModel dictModel = dictionaryMapper.selectOneByExample(dict.build());
        if (dictModel == null){
            return Collections.emptyList();
        }
        Builder values = Example.builder(DictionaryValueModel.class);
        values.where(Sqls.custom().andEqualTo("typeId",dictModel.getId()));
        return dictionaryValueMapper.selectByExample(values.build());
    }
}
