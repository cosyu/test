package com.example.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {


    @GetMapping("/aspect1")
    public ResponseEntity<String> aspect1(){
        System.out.println("run aspect1 in controller...");
        return new ResponseEntity<>("completed", HttpStatus.OK);//return with HttpStatus
    }

    @GetMapping("/aspect2")
    @HasAnyRole({"ADMIN","OPERATOR"})//required role for this method
    public ResponseEntity<String> aspect2(){
        System.out.println("run aspect2 in controller...");
        return new ResponseEntity<>("completed", HttpStatus.OK);//return with HttpStatus
    }
}
