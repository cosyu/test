package com.example.generic;

import java.util.Collection;

//
public class CollectionGeneric<T extends Collection> {

    private T collection;

    CollectionGeneric(T collection){
        this.collection = collection;
    }

    void show(){
        System.out.println(this.collection.getClass().getName());
    }
}
