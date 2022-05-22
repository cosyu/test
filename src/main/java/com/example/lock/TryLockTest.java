package com.example.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockTest {

    private List<Integer> list = new ArrayList<Integer>();

    private Lock lock = new ReentrantLock();

    public static void main(String[] args){

       final TryLockTest lockTest = new TryLockTest();

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

        //if a thread get lock fail, it will not wait for getting lock
        if(lock.tryLock()){
            try{
                System.out.println(thread.getName()+" get lock");
                Thread.sleep(10000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println(thread.getName()+"  unlock");
                lock.unlock();// unlock should be executed lastly
            }
        }else{
            System.out.println(thread.getName()+" get lock fail");
        }

    }
}
