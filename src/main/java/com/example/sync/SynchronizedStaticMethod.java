package com.example.sync;

public class SynchronizedStaticMethod implements Runnable{

    private static SynchronizedStaticMethod instance1 = new SynchronizedStaticMethod();
    private static SynchronizedStaticMethod instance2 = new SynchronizedStaticMethod();

    @Override
    public void run() {
        method();
    }

    // synchronized用在静态方法上，默认的锁就是当前所在的Class类，所以无论是哪个线程访问它，需要的锁都只有一把
    public static synchronized void method(){

            System.out.println("current thread:" + Thread.currentThread().getName()+" start");
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("current thread:" + Thread.currentThread().getName()+" end");

    }

    public static void main(String[] args){

        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        t1.start();
        t2.start();
    }
}
