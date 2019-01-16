package com.mbb.enumeration.rest.controller;

import com.mbb.basic.rest.controller.BaseController;
import com.mbb.enumeration.biz.dto.EnumerationInfoDto;
import com.mbb.enumeration.biz.dto.EnumerationQueryDto;
import com.mbb.enumeration.biz.service.EnumerationService;
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
@RequestMapping("api/v1/enumeration")
public class EnumerationController extends BaseController {

    @Autowired
    private EnumerationService enumerationService;

    @PostMapping("/info")
    public ResponseEntity getEnumerations(@RequestBody EnumerationQueryDto enumerationQueryDto) {
        List<EnumerationInfoDto> enumerationInfoDtoList = enumerationService.getEnumerations(enumerationQueryDto);
        return ResponseEntity.ok(enumerationInfoDtoList);
    }

    @PostMapping("/add")
    public ResponseEntity addStock(@RequestBody List<EnumerationInfoDto> enumerationInfoDtoList) {
        enumerationService.addEnumeration(enumerationInfoDtoList);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteEnumeration(@RequestParam(value = "id", required = true) String id) {
        enumerationService.deleteEnumeration(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
