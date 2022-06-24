package com.example.pattern.command;

public class DarkerCommand extends Command{

    public DarkerCommand(Light light){
        super(light);
    }

    @Override
    public void execute() {
        light.darker();//concrete logic
    }
}
