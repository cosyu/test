package com.example.pattern.adapter;

public class HongKongMan {

    private Adapter adapter;

    public HongKongMan(Adapter adapter) {
        this.adapter = adapter;
    }

    public void hello(){
        adapter.hello();
    }

    public void selfIntro(){
        adapter.selfIntro();
    }
}
