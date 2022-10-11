package com.example.yaml;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@Configuration
@Data
@ConfigurationProperties(prefix = "yaml")//for property under yaml in status_config2.yml
@PropertySource(value = "classpath:status_config2.yml", factory = MyPropertySourceFactory.class)//specify class to provide property source
public class YamlStatusProperties2 {

    private Map<String, List<PgaAppStatus>> application;

    private Map<String, List<PgaDocStatus>> documentStatus;
/*
    @Data
    public static class ApplicationStatus {
        private Map<String, List<PgaAppStatus>> status;
    }

    @Data
    public static class DocumentStatus {
        private Map<String, List<PgaDocStatus>> status;
    }
*/

}
