package com.example.springsecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
                    .antMatchers("/**").permitAll() //specify path is permitted,no need to auth
                    .antMatchers("/hello").hasRole("admin")//表示访问 /hello 这个接口，需要具备 admin 这个角色
                    .anyRequest() //對象為所有網址
                    .authenticated() //存取必須通過驗證
                    .and()
                    .formLogin() //若未不符合authorize條件，則產生預設login表單
                    //.loginPage("/index")
                    .loginProcessingUrl("/doLogin")
                    .defaultSuccessUrl("/queryUser")
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write("login success");
                            out.flush();
                        }
                    })
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException exception) throws IOException, ServletException {
                            resp.setContentType("application/json;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.write("login fail");
                            out.flush();
                        }
                    })
                    .permitAll()//和表单登录相关的接口统统都直接通过
                    .and()/*
                        .logout()
                        .logoutUrl("/doLogout")
                        .logoutSuccessUrl("/displayLogout")
                        .logoutSuccessHandler(new LogoutSuccessHandler(){
                            @Override
                            public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                                resp.setContentType("application/json;charset=utf-8");
                                PrintWriter out = resp.getWriter();
                                out.write("logout success");
                                out.flush();
                            }
                        })
                        .permitAll()//和表单logout相关的接口统统都直接通过
                        .and()*/
                    /**
                     * enable csrf token, it will add token to cookie(with name X-CSRF-TOKEN) which is added to response
                     * server side can get the token from request header or cookie
                     */
                    .httpBasic().and().csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }


    @Bean
    PasswordEncoder passwordEncoder() {
        //password encoder
        return new BCryptPasswordEncoder();
    }

}
