package com.example.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantWriteLockTest {


    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args){

        ReentrantWriteLockTest lockTest = new ReentrantWriteLockTest();

        new Thread(){
            public void run() {
                lockTest.write(Thread.currentThread());
            };
        }.start();

        new Thread(){
            public void run(){
                //如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。
                //lockTest.read(Thread.currentThread());
                lockTest.write(Thread.currentThread());
            };
        }.start();
    }

    public void write(Thread thread) {

        rwl.writeLock().lock();
        try {
            System.out.println(thread.getName()+" get lock");
            long start = System.currentTimeMillis();

            while(System.currentTimeMillis() - start <= 1) {
                System.out.println(thread.getName()+" is writing");
            }
            System.out.println(thread.getName()+" is completed");
        } finally {
            System.out.println(thread.getName()+"  unlock");
            rwl.writeLock().unlock();// unlock should be executed lastly
        }
    }

    public void read(Thread thread) {

        rwl.readLock().lock();
        try {
            System.out.println(thread.getName()+" get lock");
            long start = System.currentTimeMillis();

            while(System.currentTimeMillis() - start <= 1) {
                System.out.println(thread.getName()+" is reading");
            }
            System.out.println(thread.getName()+" is completed");
        } finally {
            System.out.println(thread.getName()+"  unlock");
            rwl.readLock().unlock();// unlock should be executed lastly
        }
    }
}
