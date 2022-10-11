package com.example.listener;

import com.example.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterPublisherService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;//GenericWebApplicationContext

    //this method will be called by Spring and inject ApplicationEventPublisher object
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void insert(User user){
        //insert user to db

        //publish event
        UserRegisterEvent event = new UserRegisterEvent(user);

        //invoke listener in SimpleApplicationEventMulticaster
        //then it calls listener.onApplicationEvent(event);
        applicationEventPublisher.publishEvent(event);
    }
}
