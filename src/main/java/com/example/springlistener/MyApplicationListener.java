package com.example.springlistener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//it will be created by Spring and registered(added) into observer(listener)
// list in SimpleApplicationEventMulticaster
@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        //ServletWebServerInitializedEvent
        //ContextRefreshedEvent
        //ApplicationStartedEvent
        //AvailabilityChangeEvent
        //ApplicationReadyEvent
        //AvailabilityChangeEvent
        //System.out.println("-----trigger event:" + event.getClass().getName());
    }


}
