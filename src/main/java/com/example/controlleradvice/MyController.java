package com.example.controlleradvice;

import com.example.test.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Date;
import java.util.List;


@RestController
public class MyController {

    @GetMapping("/advice1")
    public String advice1(@ModelAttribute("key1") String attribute){//get global attribute from class with @ControllerAdvice
        return attribute;
    }

    @GetMapping("/advice2")
    public String advice2(Date date){//date will be formatted with formatter which registered in class with @ControllerAdvice

        return date.toString();
    }


    @GetMapping("/advice3")
    public String advice3(){
        if(true){
            throw new RuntimeException("test exception");
        }
        return "completed";
    }


    @GetMapping("/advice4")
    public ResponseEntity<String> advice4(){
        if(true){
            throw new AppInvalidException(301, Status.METHOD_NOT_ALLOWED,"test error message");
        }
        return new ResponseEntity<String>("completed", HttpStatus.OK);//return with HttpStatus
    }
}
