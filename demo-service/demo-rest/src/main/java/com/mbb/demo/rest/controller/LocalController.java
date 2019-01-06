package {{ group }}.{{ name }}.rest.controller;


import {{ group }}.{{ name }}.biz.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/{{ name }}/api/v1/local")
public class LocalController extends BaseController {

    @Autowired
    private LocalService localService;

    @GetMapping("/{{ name }}")
    public String {{ name }}() {
        return localService.local();
    }

}
