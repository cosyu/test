package com.example.listener;

import org.springframework.context.ApplicationEvent;

//Event is observed
public class UserRegisterEvent extends ApplicationEvent {

    public UserRegisterEvent(Object source){
        super(source);
    }
}
