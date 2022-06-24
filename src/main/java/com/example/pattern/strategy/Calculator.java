package com.example.pattern.strategy;

//wrapper for IStrategy
public class Calculator {

    private IStrategy iStrategy;

    public int execute(int a,int b){
        return iStrategy.calculate(a,b);
    }

    public void setStrategy(DoType doType){
        switch (doType){
            case ADD -> {
                this.iStrategy = new Add();
                break;
            }
            case MINUS -> {
                this.iStrategy = new Minus();
                break;
            }
            case DIVIDE -> {
                this.iStrategy = new Divide();
                break;
            }
            case MULTYPLY -> {
                this.iStrategy = new Multply();
                break;
            }
        }
    }

    public enum DoType{
        ADD,MINUS,DIVIDE,MULTYPLY
    }
}
