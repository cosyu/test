package com.example.thread;

public class InterruptedTest {

    public static void main(String[] args){

        Thread t = new MyThread();
        t.start();
        try{
            Thread.sleep(10);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

        t.interrupt();//interrupt async thread
        Thread.currentThread().interrupt();//try to interrupt main thread,
        // async thread's join method throw InterruptedException
        try{
            t.join();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("end");
    }

}

class MyThread extends Thread {
    public void run() {
        int n = 0;
        while (! isInterrupted()) {
            n ++;
            System.out.println(n + " hello!");
        }
    }
}