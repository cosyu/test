package com.example.sync;

public class SynchronizedObject implements Runnable{

    private static SynchronizedObject instance = new SynchronizedObject();

    @Override
    public void run() {

        // 同步代码块形式——锁为this,两个线程使用的锁是一样的,线程1必须要等到线程0释放了该锁后，才能执行
        synchronized (this){
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

        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
