package com.example.sync;

public class SynchronizedMethod implements Runnable{

    private static SynchronizedMethod instance = new SynchronizedMethod();

    @Override
    public void run() {
        method();
    }

    //same as synchronized(this)
    public synchronized void method(){

            System.out.println("current thread:" + Thread.currentThread().getName()+" start");
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("current thread:" + Thread.currentThread().getName()+" end");

    }

    public static void main(String[] args){

        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
