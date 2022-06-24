package com.example.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Date;

public class BuilderTest {


    public static void main(String... args) throws Exception{

        Child.ChildBuilder builder = Child.builder();
//        Parent p = builder.name("Season").age(7).parentName("Jason").build();
//        System.out.println(p.toString());


    }

}
