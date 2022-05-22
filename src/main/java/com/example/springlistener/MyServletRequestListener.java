package com.example.springlistener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

@Component
public class MyServletRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized (ServletRequestEvent event) {
       //System.out.println(" requestInitialized ");
       // System.out.println("-----trigger event:" + event.getClass().getName());
    }

    @Override
    public void requestDestroyed (ServletRequestEvent event) {
        //System.out.println(" requestDestroyed ");
       // System.out.println("-----trigger event:" + event.getClass().getName());
    }
}
