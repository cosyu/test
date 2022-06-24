package com.example.pattern.command;

public class TurnOnCommand extends Command{

    public TurnOnCommand(Light light){
        super(light);
    }

    @Override
    public void execute() {
        //concrete logic
        light.turnOn();
    }
}
