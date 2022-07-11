package com.example.springlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//it will be executed after the Spring Application start up
@Component
@Slf4j
public class ApplicationStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("ApplicationStartupRunner run method Started !!");
    }
}
