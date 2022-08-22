package com.example.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
        super.configure(authBuilder);
        //authBuilder.authenticationProvider(authenticationProvider);
        //assign two users in memory
//        auth.inMemoryAuthentication()
//                .withUser("admin").roles("ADMIN", "USER").password("123")
//                .and()
//                .withUser("test").roles("USER").password("123");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

                http.authorizeRequests()
                        .antMatchers("/*").permitAll() //all path is permitted
                .anyRequest() //對象為所有網址
                .authenticated() //存取必須通過驗證
                .and()
                .formLogin() //若未不符合authorize條件，則產生預設login表單
                        //.loginPage("/index")
                        .loginProcessingUrl("/doLogin")
                        .defaultSuccessUrl("/queryUser")
                .and()
                .httpBasic().and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        //password encoder
        return new BCryptPasswordEncoder();
    }

}
