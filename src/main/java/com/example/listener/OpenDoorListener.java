package com.example.listener;

public class OpenDoorListener implements DoorListener{

    @Override
    public void listenDoorEvent(DoorEvent doorEvent) {
        if(doorEvent.getStatus() == 1){
            System.out.println("the door is open");
        }
    }
}
