package com.example.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Date;

public class JacksonTest {


    public static void main(String... args) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();

        Student.StudentBuilder studentBuilder = Student.builder();
        studentBuilder.id(10).name("Jason Yu").title("Mrs").gender(1).
                age(30).updateTime(new Date()).updateTime2(LocalDateTime.now());

        String jsonStr = objectMapper.writeValueAsString(studentBuilder.build());
        System.out.println(jsonStr);
        
        Student stu = objectMapper.readValue(jsonStr,Student.class);
        System.out.println(stu.toString());

    }

}
