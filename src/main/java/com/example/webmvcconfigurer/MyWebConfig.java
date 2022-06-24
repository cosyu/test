package com.example.webmvcconfigurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//It is used to define customer Handler，Interceptor，ViewResolver，MessageConverter, CORS
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    private String[] corsOrigins = {"https://www.tradesinglewindow.hk","https://local.tradesinglewindow.hk:3000"};

    private String[] corsMethods = {"GET","POST","HEAD","OPTIONS","PUT","DELETE","PATCH"};

    private String[] corsHeaders = {"AUTHORIZATION","CONTENT-TYPE","X-CSRF-TOKEN"};

    private int corsMaxAge = 1800;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**") //it means all path
                .allowedOrigins(corsOrigins)
                .allowCredentials(true)
                .allowedMethods(corsMethods)
                .allowedHeaders(corsHeaders)
                .maxAge(corsMaxAge);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MyWebInterceptor()).addPathPatterns("/**");//all path will be intercepted

    }

}
