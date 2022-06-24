package com.example.pattern.command;

public class BrighterCommand extends Command{

    public BrighterCommand(Light light){
        super(light);
    }

    @Override
    public void execute() {
        light.brighter();//concrete logic
    }
}
