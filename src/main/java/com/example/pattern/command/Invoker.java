package com.example.pattern.command;

import java.util.ArrayList;
import java.util.List;

//wrapper for command list
public class Invoker {

    private List<Command> commandList = new ArrayList<Command>();

    public Invoker addCommand(Command command){
        commandList.add(command);
        return this;
    }

    public void execute(){
        for(Command command : commandList){
            command.execute();
        }
    }
}
