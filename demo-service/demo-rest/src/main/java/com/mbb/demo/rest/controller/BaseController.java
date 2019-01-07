package {{ group }}.{{ name }}.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

@Slf4j
@RequestMapping("/api/v1")
public class BaseController {


    @ExceptionHandler({Exception.class})
    public ResponseEntity handleException(Exception e) {
        if (log.isWarnEnabled()) {
            log.error("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
    }


}
