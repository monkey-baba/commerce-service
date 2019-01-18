package com.mbb.basic.rest.controller;

import com.mbb.basic.biz.dictionaryvalue.biz.dto.DictionaryValueInfoDto;
import com.mbb.basic.biz.dictionaryvalue.biz.dto.DictionaryValueQueryDto;
import com.mbb.basic.biz.dictionaryvalue.biz.dto.DictionaryValueResponse;
import com.mbb.basic.biz.dictionaryvalue.biz.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * ${DESCRIPTION}
 *
 * @author lf
 * @create 2019-01-08 15:33
 */
@RestController
@RequestMapping("/api/v1/dictionaryvalue")
public class DictionaryValueController extends BaseController {

    @Autowired
    private DictionaryValueService dictionaryValueService;

    
    @PostMapping("/info")
    public ResponseEntity getDictionaryValue(@RequestBody DictionaryValueQueryDto dictionaryValueQueryDto) {
        List<DictionaryValueResponse> dictionaryValueInfoDtoList = dictionaryValueService.getDictionaryValues(dictionaryValueQueryDto);
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
