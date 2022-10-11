package com.example.annotation.json;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Date;

public class JacksonTest {


    public static void main(String... args) throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        //this setting will ignore transient variable
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

        Student.StudentBuilder studentBuilder = Student.builder();
        studentBuilder.id(10).name("Jason Yu").title("Mrs").password("123456").gender(1).
                age(30).updateTime(new Date()).updateTime2(LocalDateTime.now());

        String jsonStr = objectMapper.writeValueAsString(studentBuilder.build());
        System.out.println(jsonStr);
        
        Student stu = objectMapper.readValue(jsonStr,Student.class);
        System.out.println(stu.toString());

    }

}
