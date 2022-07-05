package com.example.webmvcconfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

//It is used to define customer Handler，Interceptor，ViewResolver，MessageConverter, CORS
@Configuration
public class WebConfig implements WebMvcConfigurer {

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

        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");//all path will be intercepted
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }


}
