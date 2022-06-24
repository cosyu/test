package com.example.pattern.template;

public abstract class AbstractService {

    public void preRun(){
        System.out.println("pre run");
    }

    public void postRun(){
        System.out.println("post run");
    }

    abstract public void run();

    public void execute(){
        preRun();
        run();
        postRun();
    }
}
