package com.example.webmvcconfigurer;

import com.example.controlleradvice.AppInvalidException;
import com.example.controlleradvice.MyRuntimeException;
import org.apache.http.client.utils.DateUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.zalando.problem.Status;

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
        //System.out.println("Interceptor preHandler method is running !");
//        if(true){
//            throw new MyRuntimeException("exception form WebInterceptor...");
//            //throw new AppInvalidException(301, Status.METHOD_NOT_ALLOWED,"test error message");
//        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();

        if(request.equals(httpServletRequest)){
            //System.out.println("request equals to httpServletRequest");
        }

        System.out.println("\n\n------get session-------");
        HttpSession session = httpServletRequest.getSession();
        System.out.println("session id:"+session.getId());
        Enumeration<String> attNames = session.getAttributeNames();
        while (attNames.hasMoreElements()){
            String name = attNames.nextElement();
            Object att = session.getAttribute(name);
            System.out.println("session attribute:"+name+":"+att);
        }
        Date date1 = new Date(session.getLastAccessedTime());
        //System.out.println("session last access time:"+DateUtils.formatDate(date1));
        Date date2 = new Date(session.getCreationTime());
        //System.out.println("session create time:"+DateUtils.formatDate(date2));

        //get header from request
        System.out.println("\n\n------get request header-------");
        Enumeration<String> headerNames =  httpServletRequest.getHeaderNames();
        //missing X-CSRF-TOKEN
        while (headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            String header = httpServletRequest.getHeader(name);
            System.out.println(name+":"+header);
        }
        System.out.println("\n\n------get host header from request-------");
        Enumeration<String> hostHeader =  httpServletRequest.getHeaders("host");//get header values instead of header name
        while (hostHeader.hasMoreElements()){
            System.out.println(hostHeader.nextElement());
        }

        //String csrfInHeader = httpServletRequest.getHeader("X-CSRF-TOKEN");
        System.out.println("\n\n------get cookie-------");
        //get cookie from request
        Cookie[] cookies = httpServletRequest.getCookies();
        if (!ObjectUtils.isEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                //System.out.println(cookie);
                System.out.println(cookie.getName()+":"+cookie.getValue());
                if (cookie.getName().equals("customerId2")) {
                    System.out.println("customerId2's detail:"+cookie.getMaxAge()+"-"+cookie.getDomain()+"-"+cookie.getPath());
                }
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       // System.out.println("Interceptor postHandler method is running !");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //System.out.println("Interceptor afterCompletion method is running !");
    }

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
