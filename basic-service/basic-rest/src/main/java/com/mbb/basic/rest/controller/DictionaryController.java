package com.mbb.basic.rest.controller;


import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.service.DictionaryService;
import com.mbb.basic.common.dto.DictValueData;
import java.util.List;
import java.util.stream.Collectors;

import com.mbb.dictionaryvalue.biz.model.DictionaryValueModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dict")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 根据Type获取枚举值
     * @param type 枚举类型字符串
     * @return 枚举值List
     */
    @GetMapping("/values")
    public ResponseEntity values(@RequestParam String type){

        List<DictionaryModel> values = dictionaryService.findDictValues(type);
        List<DictValueData> result = values.stream().map(value -> {
            DictValueData dictValueData = new DictValueData();
            BeanCopier.create(DictionaryValueModel.class, DictValueData.class, false)
                    .copy(value, dictValueData, null);
            return dictValueData;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }


}
