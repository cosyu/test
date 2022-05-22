package com.example.pattern.factory;

public interface Factory {

    public Product getProduct();

    public Product getProduct(String state);
}
