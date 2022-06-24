package com.example.test;

import com.example.pattern.strategy.Calculator;
import com.example.pattern.template.AbstractService;
import com.example.pattern.template.ServiceA;
import com.example.pattern.template.ServiceB;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TemplateTests {

    public TemplateTests(){

    }

    @Test
    public void test(){

        AbstractService serviceA = new ServiceA();
        serviceA.execute();
        AbstractService serviceB = new ServiceB();
        serviceB.execute();
    }
}
