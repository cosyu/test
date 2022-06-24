package com.example.pattern.command;

public abstract class Command {

    protected Light light;

    public Command(Light light){
        this.light = light;
    }

    public abstract void execute();
}
