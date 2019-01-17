package {{ group }}.{{ name }}.app;

{% if orm == 'mapper'  %}
import tk.mybatis.spring.annotation.MapperScan;
{% endif %}
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
{% if platform == 'cloud' %}
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
{% endif %}
{% if feign  %}
import org.springframework.cloud.openfeign.EnableFeignClients;
{% endif %}


@SpringBootApplication(scanBasePackages = "{{ group }}.{{ name }}")
{% if platform == 'cloud' %}
@EnableDiscoveryClient
{% endif %}
{% if orm == 'mapper'  %}
@MapperScan(basePackages = "{{ group }}.{{ name }}.biz.dao")
{% endif %}
{% if feign  %}
@EnableFeignClients
{% endif %}
public class {{ name | title }}ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run({{ name | title }}ServiceApplication.class, args);
    }

}
