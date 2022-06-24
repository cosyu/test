package com.example.pattern.command;

public class TurnOffCommand extends Command{

    public TurnOffCommand(Light light){
        super(light);
    }

    @Override
    public void execute() {
        light.turnOff();//concrete logic
    }
}
