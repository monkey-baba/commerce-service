package {{ group }}.api;

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
    String remote{{ name | title}}();

}
