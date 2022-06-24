package com.example.test;

import com.example.pattern.command.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommandTests {

    public CommandTests(){

    }

    @Test
    public void test(){

        Light light = new Light();
        Command turnOnCommand = new TurnOnCommand(light);
        Command turnOfCommand = new TurnOffCommand(light);
        Command brighterCommand = new BrighterCommand(light);
        Command darkerCommand = new DarkerCommand(light);

        Invoker invoker = new Invoker();
        invoker.addCommand(turnOnCommand).addCommand(turnOfCommand)
                .addCommand(brighterCommand).addCommand(darkerCommand)
                .execute();

    }
}
