package com.example.yaml;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "system") // for property under system in system_config.yml
@PropertySource(value = "classpath:system_config.yml", factory = MyPropertySourceFactory.class)//specify class to provide property source
public class MySystemProperty {

    private String name;
    private String version;
}
