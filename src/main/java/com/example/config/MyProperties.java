package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource({"classpath:template.properties"})
public class MyProperties {
    @Autowired
    private Environment env;

    private static Environment environment;

    @PostConstruct
    private void initEnv(){
        environment = env;
    }

    public static String getProperty(String property) {
        return environment.getRequiredProperty(property);//can read property from different files
    }

}
