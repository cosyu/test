package com.example.listener;

import com.example.test.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//listener is observer,it will be created by Spring and registered(added) into observer(listener) list in SimpleApplicationEventMulticaster
@Component
public class UserRegisterListener implements ApplicationListener<UserRegisterEvent> {

    //listen the change of event(observed), add customer business logic
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        User source = (User)event.getSource();
        System.out.println("insert user, source is:"+source.toString());
    }
}
