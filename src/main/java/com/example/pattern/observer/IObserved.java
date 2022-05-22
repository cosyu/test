package com.example.pattern.observer;

//event
public interface IObserved {

    public void add(IObserver observer);
    public void remove(IObserver observer);
    public void notifyObservers();
    public String getName();
    public void setName(String name);
}
