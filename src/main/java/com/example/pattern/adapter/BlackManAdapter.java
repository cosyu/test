package com.example.pattern.adapter;

//Adapter is used to translate black man
public class BlackManAdapter extends Adapter{

    public BlackManAdapter(String name) {
        super(name);
    }

    //required business logic for client side
    @Override
    public void hello() {
        System.out.println("--- translator----"+getName());
    }

    //required business logic for client side
    @Override
    public void selfIntro() {
        System.out.println("--- translator----"+"Hello, I am "+getName()+
                ",I am working in HongKong now");
    }
}
