# {{ name | title }} Api

### Api工程
此工程写Service发布给其他工程调用的Dubbo Interface 或者 Feign Interface，由调用方的adapter模块依赖

### 示例
```
{% if platform == 'cloud' %}
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
{% endif %}
/**
 * 用于声明消费者消费自己的服务
 */
{% if platform == 'cloud' %}
@FeignClient(name = "{{ name }}-service")
{% endif %}
public interface RemoteService {

    /**
     * 示例:调用自己的接口
     * @return result
     */
    {% if platform == 'cloud' %}
    @GetMapping("/api/v1/{{ name }}")
    {% endif %}
    String remote();

}
```