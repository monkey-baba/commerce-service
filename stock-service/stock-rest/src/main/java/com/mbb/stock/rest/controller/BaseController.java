package com.mbb.stock.rest.controller;

import com.mbb.common.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/stock/api/v1")
public class BaseController {


    @ExceptionHandler({Exception.class})
    public ResultDto handleException(Exception e) {
        if (log.isWarnEnabled()) {
            log.error("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage(), e);
        }
        return ResultDto.fail(e.getMessage());
    }


}
