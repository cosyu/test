package com.example.pattern.command;

//command receiver
public class Light {

    public void turnOn(){
        System.out.println("turn on the light");
    }

    public void turnOff(){
        System.out.println("turn off the light");
    }

    public void brighter(){
        System.out.println("brighter light");
    }

    public void darker(){
        System.out.println("darker the light");
    }
}
