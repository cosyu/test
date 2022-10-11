package com.example.listener;

import com.example.domain.User;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//listener is observer, it will be created by Spring and registered(added) into observer(listener) list in SimpleApplicationEventMulticaster
@Component
public class UserNotifyListener implements ApplicationListener<UserRegisterEvent> {

    //listen the change of event(observed), add customer business logic
    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        User source = (User)event.getSource();
        System.out.println("notify user, source is:"+source.toString());
    }
}
