package com.mbb.dictionary.rest.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mbb.basic.biz.model.DictionaryValueValueModel;
import com.mbb.basic.rest.controller.BaseController;
import com.mbb.dictionaryValue.biz.dto.DictionaryValueInfoDto;
import com.mbb.dictionaryValue.biz.dto.DictionaryValueListQuery;
import com.mbb.dictionaryValue.biz.dto.DictionaryValueListResp;
import com.mbb.dictionaryValue.biz.dto.DictionaryValueQueryDto;
import com.mbb.dictionaryValue.biz.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:33
 */
@RestController
@RequestMapping("api/v1/dictionaryValue")
public class DictionaryValueValueController extends BaseController {

    @Autowired
    private DictionaryValueService dictionaryValueService;

    
    @PostMapping("/info")
    public ResponseEntity getDictionaryValue(@RequestBody DictionaryValueQueryDto dictionaryValueQueryDto) {
        List<DictionaryValueInfoDto> dictionaryValueInfoDtoList = dictionaryValueService.getDictionaryValues(dictionaryValueQueryDto);
        return ResponseEntity.ok(dictionaryValueInfoDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity addDictionaryValue(@RequestBody List<DictionaryValueInfoDto> dictionaryValueInfoDtoList) {
        dictionaryValueService.addDictionaryValue(dictionaryValueInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteDictionaryValue(@RequestParam(value = "id", required = true) String id) {
        dictionaryValueService.deleteDictionaryValue(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
