package com.example.pattern.strategy;

public class Minus implements IStrategy{

    //concrete algorithm
    @Override
    public int calculate(int a, int b) {
        int res = a - b;
        System.out.println("result is "+res);
        return res;
    }
}
