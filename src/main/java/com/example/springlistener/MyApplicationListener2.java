package com.example.springlistener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;

//another way to implement listener
//it will be created by Spring and registered(added) into observer(listener)
// list in SimpleApplicationEventMulticaster
@Component
public class MyApplicationListener2{

    @EventListener(classes = {ApplicationEvent.class})
    public void onApplicationEvent(ApplicationEvent event) {
        //System.out.println("-----trigger event:" + event.getClass().getName());
    }

    @EventListener(classes = {ServletContextEvent.class})
    public void onServletContextEvent(ServletContextEvent event) {
        //System.out.println("-----trigger event:" + event.getClass().getName());
    }

}
