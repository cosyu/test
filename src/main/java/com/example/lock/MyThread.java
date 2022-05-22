package com.example.lock;

public class MyThread extends Thread{

    private LockInterruptedTest lockInterruptedTest;

    public MyThread(LockInterruptedTest lockInterruptedTest){
        this.lockInterruptedTest = lockInterruptedTest;
    }

    @Override
    public void run(){
        try{
            //multiple thread will call same method
            lockInterruptedTest.insert(Thread.currentThread());
        }catch (InterruptedException ex){
            System.out.println(Thread.currentThread()+getName()+" is interrupted!");
        }
    }
}
