package com.example.pattern.dynamicproxy;

public class DataServiceImplA implements IDataService{

    @Override
    public void add() {
        System.out.println("-------Service A add");
    }

    @Override
    public void delete() {
        System.out.println("-------Service A delete");
    }

    @Override
    public String update(String p) {
        System.out.println("-------Service A update");
        return "A update success";
    }

}
