package com.example.listener;

import java.util.EventObject;

public class DoorEvent extends EventObject {

    private Integer status;

    public DoorEvent(Object source){
        super(source);
    }

    public DoorEvent(Object source, Integer status){
        super(source);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
