package {{ group }}.rest.controller;


import {{ group }}.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/{{ name }}")
    public String {{ name }}() {
        return localService.local();
    }

}
