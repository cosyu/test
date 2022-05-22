package com.example.listener;

public class CloseDoorListener implements DoorListener{

    @Override
    public void listenDoorEvent(DoorEvent doorEvent) {
        if(doorEvent.getStatus() == 0){
            System.out.println("the door is close");
        }
    }
}
