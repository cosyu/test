package com.example.listener;

import com.example.test.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserInfoCheckListener {

    @Async("userInfoPool")
    @EventListener(classes = UserRegisterEvent.class)
    public void checkUserInfo(UserRegisterEvent event){
        User source = (User)event.getSource();
        System.out.println("check user, source is:"+source.toString());
    }
}
