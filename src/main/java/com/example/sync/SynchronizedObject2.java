package com.example.sync;

public class SynchronizedObject2 implements Runnable{

    private static SynchronizedObject2 instance = new SynchronizedObject2();

    Object block1 = new Object();
    Object block2 = new Object();

    @Override
    public void run() {

        // 这个代码块使用的是第一把锁，当他释放后，后面的代码块由于使用的是第二把锁，因此可以马上执行
        synchronized (block1){
            System.out.println("block1,current thread:" + Thread.currentThread().getName()+" start");
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("block1,current thread:" + Thread.currentThread().getName()+" end");
        }

        synchronized (block2){
            System.out.println("block2,current thread:" + Thread.currentThread().getName()+" start");
            try{
                Thread.sleep(3000);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("block2,current thread:" + Thread.currentThread().getName()+" end");
        }

    }

    public static void main(String[] args){

        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();

    }
}
