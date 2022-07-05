package com.example.springlistener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

@Component
public class MyServletRequestListener implements ServletRequestListener {

    /*
    The end-to-end flow of request

    *  requestInitialized
Interceptor preHandler method is running !
Interceptor postHandler method is running !
Interceptor afterCompletion method is running !
-----trigger event:org.springframework.web.context.support.ServletRequestHandledEvent
 requestDestroyed

    * */
    //it will be executed when the request is initialized
    @Override
    public void requestInitialized (ServletRequestEvent event) {
       //System.out.println(" requestInitialized ");
       // System.out.println("-----trigger event:" + event.getClass().getName());
    }

    //it will be executed when the request is completed
    @Override
    public void requestDestroyed (ServletRequestEvent event) {
       // System.out.println(" requestDestroyed ");
       // System.out.println("-----trigger event:" + event.getClass().getName());
    }
}
