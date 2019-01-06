package {{ group }}.{{ name }}.app;

import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication(scanBasePackages = "{{ group }}.{{ name }}")
{% if platform == 'cloud' %}
@EnableDiscoveryClient
{% endif %}
{% if orm == 'mapper'  %}
@MapperScan(basePackages = "{{ group }}.{{ name }}.biz.dao")
{% endif %}
public class {{ name | title }}ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run({{ name | title }}ServiceApplication.class, args);
    }

}
