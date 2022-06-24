package com.example.test;

import com.example.pattern.strategy.Calculator;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StrategyTests {

    public StrategyTests(){

    }

    @Test
    public void test(){

        Calculator calculator = new Calculator();
        calculator.setStrategy(Calculator.DoType.ADD);
        calculator.execute(10,20);
        calculator.setStrategy(Calculator.DoType.MULTYPLY);
        calculator.execute(10,20);
    }
}
