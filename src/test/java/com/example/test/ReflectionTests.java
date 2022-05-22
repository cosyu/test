package com.example.test;

import com.example.pattern.proxy.Agent;
import com.example.pattern.proxy.IBuyHouse;
import com.example.pattern.proxy.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReflectionTests {

    public ReflectionTests(){

    }

    @Test
    public void test() throws Exception{

        String str = "abc";
        Class clazz1 = str.getClass();
        Class clazz2 = String.class;
        Class clazz3 = Class.forName("java.lang.String");
        //clazz1 == clazz2 == clazz3
        System.out.println(clazz1);
        System.out.println(clazz2);
        System.out.println(clazz3);
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz3 == clazz2);


        User user = new User();
        Class clazz4 = user.getClass();
        Class clazz5 = User.class;
        Class clazz6 = Class.forName("com.example.test.User");
        //clazz4 == clazz5 == clazz6
        System.out.println(clazz4);
        System.out.println(clazz5);
        System.out.println(clazz6);
        System.out.println(clazz4 == clazz5);
        System.out.println(clazz5 == clazz6);

    }
}
