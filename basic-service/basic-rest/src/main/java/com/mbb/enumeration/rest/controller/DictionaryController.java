package com.mbb.dictionary.rest.controller;

import com.mbb.basic.rest.controller.BaseController;
import com.mbb.dictionary.biz.dto.DictionaryListQuery;
import com.mbb.dictionary.biz.dto.DictionaryListResp;
import com.mbb.dictionary.biz.dto.DictionaryInfoDto;
import com.mbb.dictionary.biz.dto.DictionaryQueryDto;
import com.mbb.dictionary.biz.service.DictionaryService;
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
@RequestMapping("api/v1/dictionary")
public class DictionaryController extends BaseController {

    @Autowired
    private DictionaryService dictionaryService;


    @PostMapping("/info")
    public ResponseEntity getDictionarys(@RequestBody DictionaryQueryDto dictionaryQueryDto) {
        List<DictionaryInfoDto> dictionaryInfoDtoList = dictionaryService.getDictionarys(dictionaryQueryDto);
        return ResponseEntity.ok(dictionaryInfoDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity addDictionarys(@RequestBody List<DictionaryInfoDto> dictionaryInfoDtoList) {
        dictionaryService.addDictionary(dictionaryInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteDictionary(@RequestParam(value = "id", required = true) String id) {
        dictionaryService.deleteDictionary(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
