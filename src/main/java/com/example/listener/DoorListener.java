package com.example.listener;

import java.util.EventListener;

public interface DoorListener extends EventListener {

    //listen event
    public void listenDoorEvent(DoorEvent doorEvent);

}
