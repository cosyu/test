package com.example.pattern.factory;

public class FrenchFries implements Product{

    private String state = "salt";

    public FrenchFries(){}

    public FrenchFries(String state){
        this.state = state;
    }

    @Override
    public void describe() {
        System.out.println("product state: "+ state);
    }
}
