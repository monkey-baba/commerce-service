package com.mbb.basic.rest.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.biz.dictionaryvalue.biz.model.DictionaryValueModel;
import com.mbb.basic.biz.dto.DictionaryInfoDto;
import com.mbb.basic.biz.dto.DictionaryInfoResponse;
import com.mbb.basic.biz.dto.DictionaryQueryDto;
import com.mbb.basic.biz.model.DictionaryModel;
import com.mbb.basic.biz.service.DictionaryService;
import com.mbb.basic.common.dto.DictValueData;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/dict")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private HttpSession sessionService;

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

    @PostMapping("/info")
    public ResponseEntity getDictionarys(@RequestBody DictionaryQueryDto dictionaryQueryDto) {

        //开启分页
        PageHelper.startPage(dictionaryQueryDto.getPage(), dictionaryQueryDto.getPageSize());

        List<DictionaryInfoResponse> dictionaryInfoDtoList = dictionaryService.getDictionarys(dictionaryQueryDto);
        //用data生成新的分页数据
        PageInfo<DictionaryInfoResponse> result = PageInfo.of(dictionaryInfoDtoList);
        //把原来的总条数复制进去
        if(dictionaryInfoDtoList!=null) {
            result.setTotal(Long.parseLong(sessionService.getAttribute("dictionaryTotalSize").toString()));
        }else{
            result.setTotal(0);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity addDictionarys(@RequestBody DictionaryInfoDto dictionaryInfoDto) {
        dictionaryService.addDictionary(dictionaryInfoDto);
        DictionaryInfoResponse resp = new DictionaryInfoResponse();
        resp.setName(dictionaryInfoDto.getName());
        resp.setCode(dictionaryInfoDto.getCode());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteDictionary(@RequestParam(value = "id", required = true) String id) {
        dictionaryService.deleteDictionary(id);
        return ResponseEntity.ok("删除成功");
    }
}
