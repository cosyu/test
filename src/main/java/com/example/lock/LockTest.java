package com.example.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

    private List<Integer> list = new ArrayList<Integer>();

    private Lock lock = new ReentrantLock();

    public static void main(String[] args){

        final LockTest lockTest = new LockTest();

        new Thread(){
            public void run() {
                lockTest.insert(Thread.currentThread());
            };
        }.start();

        new Thread(){
            public void run(){
                lockTest.insert(Thread.currentThread());
            };
        }.start();
    }

    public void insert(Thread thread){

        //it will lock, other thread need to wait until it is unlocked
        lock.lock();
        try{
            System.out.println(thread.getName()+" get lock");
            Thread.sleep(10000);
            for(int i=0;i<5;i++) {
                list.add(i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(thread.getName()+"  unlock");
            lock.unlock();// unlock should be executed lastly
        }
    }
}
