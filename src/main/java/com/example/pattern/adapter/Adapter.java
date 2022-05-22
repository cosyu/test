package com.example.pattern.adapter;

//Adapter is used to make required business for client side
public abstract class Adapter {

    private String name;

    public Adapter(String name){
        this.name = name;
    }

    //required business logic for client side
    public abstract void hello();

    //required business logic for client side
    public abstract void selfIntro();

    public String getName() {
        return name;
    }
}
