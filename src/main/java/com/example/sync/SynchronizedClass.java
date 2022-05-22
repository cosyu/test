package com.example.sync;

public class SynchronizedClass implements Runnable{

    private static SynchronizedClass instance1 = new SynchronizedClass();
    private static SynchronizedClass instance2 = new SynchronizedClass();

    @Override
    public void run() {

        // 所有线程需要的锁都是同一把
        synchronized (SynchronizedClass.class){
            System.out.println("current thread:" + Thread.currentThread().getName()+" start");
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("current thread:" + Thread.currentThread().getName()+" end");
        }

    }

    public static void main(String[] args){

        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
