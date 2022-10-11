package com.example.webmvcconfigurer;

import com.example.config.ApplicationContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

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

        registry.addInterceptor(ApplicationContextProvider.getBean(WebInterceptor.class)).addPathPatterns("/**");//all path will be intercepted
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

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        //for removing stackTrace of ResponseEntity<Problem> which is returned by ProblemHandling
        //for detail, please refer to ControllerHandler.java
        objectMapper.registerModules(new ProblemModule(),
                new ConstraintViolationProblemModule());
        return objectMapper;
    }

}
