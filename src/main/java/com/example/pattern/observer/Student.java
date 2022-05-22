package com.example.pattern.observer;

public class Student implements IObserver{

    private IObserved observerable;

    public Student(IObserved observerable){
        this.observerable = observerable;
    }

    //listener to observer the changes of IObserved
    @Override
    public void update() {
        System.out.println("listen:" + observerable.getName());
    }
}
