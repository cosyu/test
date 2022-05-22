package com.example.pattern.dynamicproxy;

public class DataServiceImplB implements IDataService{

    @Override
    public void add() {
        System.out.println("---------Service B add");
    }

    @Override
    public void delete() {
        System.out.println("---------Service B delete");
    }

    @Override
    public String update(String p) {
        System.out.println("-------Service B update");
        return "B update success";
    }
}
