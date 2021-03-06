package com.example.webmvcconfigurer;

import org.apache.http.client.utils.DateUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//Interceptor for web controller, can be used for checking session
public class WebInterceptor implements HandlerInterceptor {

    //this method will be called before controller
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println("Interceptor preHandler method is running !");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();

        if(request.equals(httpServletRequest)){
            //System.out.println("request equals to httpServletRequest");
        }

        System.out.println("------get session-------");
        HttpSession session = httpServletRequest.getSession();
        System.out.println("session id:"+session.getId());
        Enumeration<String> attNames = session.getAttributeNames();
        while (attNames.hasMoreElements()){
            String name = attNames.nextElement();
            Object att = session.getAttribute(name);
            System.out.println("session attribute:"+name+"-"+att);
        }
        Date date1 = new Date(session.getLastAccessedTime());
        //System.out.println("session last access time:"+DateUtils.formatDate(date1));
        Date date2 = new Date(session.getCreationTime());
        //System.out.println("session create time:"+DateUtils.formatDate(date2));

        //get header from request
        System.out.println("------get header-------");
        Enumeration<String> headerNames =  httpServletRequest.getHeaderNames();
        //missing X-CSRF-TOKEN
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            String header = httpServletRequest.getHeader(name);
            System.out.println(name+"-"+header);
        }

        //String csrfInHeader = httpServletRequest.getHeader("X-CSRF-TOKEN");
        System.out.println("------get cookie-------");
        //get cookie from request
        List<Cookie> retCookies = new ArrayList<>();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                //System.out.println(cookie);
                //missing X-ACCESS-TOKEN
                System.out.println(cookie.getName()+"-"+cookie.getValue());
                if (cookie.getName().equals("X-ACCESS-TOKEN")) {
                    retCookies.add(cookie);
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("Interceptor postHandler method is running !");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("Interceptor afterCompletion method is running !");
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
