package com.example.test;

import com.example.pattern.abstractfactory.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AbstractFactoryTests {

    public AbstractFactoryTests(){

    }

    @Test
    public void test(){

        System.out.println("----- SUV Factory -----");
        AbstractFactory abstractFactory = new SUVFactory();
        Audi audi = abstractFactory.createAudi();
        System.out.println(audi.toString());
        BMW bmw = abstractFactory.createBMW();
        System.out.println(bmw.toString());

        /*
        * if client wants to get another product, it only needs to change
        * another factory and no need to know how to create concrete product.
        * */
        System.out.println("----- Jeep Factory -----");
        abstractFactory = new JeepFactory();
        audi = abstractFactory.createAudi();
        System.out.println(audi.toString());
        bmw = abstractFactory.createBMW();
        System.out.println(bmw.toString());
    }
}
