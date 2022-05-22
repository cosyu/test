package com.example.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptedTest {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args){

        LockInterruptedTest lockInterruptedTest = new LockInterruptedTest();
        MyThread thread1 = new MyThread(lockInterruptedTest);
        MyThread thread2 = new MyThread(lockInterruptedTest);
        thread1.start();
        thread2.start();

        try{
            Thread.sleep(5000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

        //it interrupts thread which is waiting for getting lock
        thread2.interrupt();
    }

    //this method will be called by multiple thread
    public void insert(Thread thread) throws InterruptedException{

        //the thread waits for lock can be interrupted, if thread is interrupted,
        //it will not wait for getting lock
        lock.lockInterruptibly();
        try{
            System.out.println(thread.getName()+" get lock");
            Thread.sleep(10000);
//            long startTime = System.currentTimeMillis();
//            for(    ;     ;) {
//                if(System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
//                    break;
//                //插入数据
//            }
        }catch (InterruptedException ex){
            System.out.println(thread.getName() + ex.getMessage());
        }finally {
            System.out.println(thread.getName()+" unlock");
            lock.unlock();//it should be executed lastly
        }
    }

}
