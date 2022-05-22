package com.example.pattern.factory;

public class FrenchFriesFactory implements Factory{


    @Override
    public Product getProduct() {
        return new FrenchFries();
    }

    @Override
    public Product getProduct(String state){
        return new FrenchFries(state);
    }
}
