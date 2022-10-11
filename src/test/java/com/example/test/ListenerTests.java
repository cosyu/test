package com.example.test;

import com.example.domain.User;
import com.example.listener.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ListenerTests {

    @Autowired
    private UserRegisterPublisherService userRegisterPublisherService;

    public ListenerTests(){

    }

    @Test
    public void test() throws Exception{

        List<DoorListener> listenerList = new ArrayList<>();
        listenerList.add(new OpenDoorListener());
        listenerList.add(new CloseDoorListener());
        DoorEvent doorEvent = new DoorEvent("open",1);
        for(DoorListener listener : listenerList){
            listener.listenDoorEvent(doorEvent);
        }

    }

    @Test
    public void test2() throws Exception{

        User user = new User();
        user.setName("Admin");
        user.setPassword("123");
        userRegisterPublisherService.insert(user);
    }
}
