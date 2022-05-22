package com.example.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class PodcastA implements IObserved {

    //list of observer(listener)
    List<IObserver> list = new ArrayList<IObserver>();
    private String name = "English Podcast";

    //register observer(listener)
    @Override
    public void add(IObserver observer) {
        list.add(observer);
    }

    @Override
    public void remove(IObserver observer) {
        list.remove(observer);
    }

    //to notify observers when it changes states
    @Override
    public void notifyObservers() {
        for(IObserver o:list){
            o.update();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
