package com.example.springlistener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


@Component
public class MyServletContextListener implements ServletContextListener {

    //it will be executed before ServletWebServerInitializedEvent
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Servlet context initialized.");
        //System.out.println("-----trigger event:" + event.getClass().getName());
    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Servlet context destroyed.");
        //System.out.println("-----trigger event:" + event.getClass().getName());
    }

}
