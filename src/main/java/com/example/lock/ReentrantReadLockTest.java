package com.example.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadLockTest {


    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args){

        ReentrantReadLockTest lockTest = new ReentrantReadLockTest();

        new Thread(){
            public void run() {
                lockTest.read(Thread.currentThread());
            };
        }.start();

        new Thread(){
            public void run(){
                //other thread request read lock, no need to wait
                //lockTest.read(Thread.currentThread());
                //如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
                lockTest.write(Thread.currentThread());
            };
        }.start();
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
}
