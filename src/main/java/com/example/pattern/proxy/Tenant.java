package com.example.pattern.proxy;

public class Tenant implements IBuyHouse{

    @Override
    public void findHouse() {
        System.out.println("Tenant wants to  find house");
    }

    @Override
    public void finish() {
        System.out.println("Tenant agrees to rent");
    }
}
