package com.example.pattern.proxy;

public class Agent implements IBuyHouse{

    private IBuyHouse iBuyHouse;

    public Agent(IBuyHouse iBuyHouse){
        this.iBuyHouse = iBuyHouse;
    }

    /*
    * Agent do action for client, and has its pre request and post request
    * */
    @Override
    public void findHouse() {
        System.out.println("Agent prepare for finding house");
        iBuyHouse.findHouse();
        System.out.println("Agent finds house for tenant");
    }

    @Override
    public void finish() {
        System.out.println("Agent prepare for process");
        iBuyHouse.finish();
        System.out.println("Agent finish the rent process");
    }
}
