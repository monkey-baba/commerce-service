# Customer Adaptor

### 适配器工程
由Biz工程出发的微服务调用，由此工程进行适配到Dubbo或Spring Cloud的微服务接口

### 示例
```
import org.springframework.stereotype.Service;

/**
 * 此处写此服务调用别的服务的适配器Adapter
 */
@Service
public class RemoteAdapter {

    @Autowired
    private RemoteService remoteService;

    public String adapter(){
        //远程调用
        return remoteService.remote();
    }

}
```
