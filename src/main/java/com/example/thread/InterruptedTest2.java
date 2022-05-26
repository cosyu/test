package com.example.thread;

public class InterruptedTest2 {

    public static void main(String[] args){

        Thread t = new MyThread2();
        t.start();
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

        t.interrupt();//interrupt async thread
        try{
            t.join();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("end");
    }

}

class MyThread2 extends Thread {
    public void run() {
        Thread hello = new HelloThread();
        hello.start();
        try{
            hello.join();//current is waiting for hello thread, it will throw InterruptedException is current thread is interrupted
        }catch (InterruptedException ex){
            System.out.println("interrupted");
        }
        hello.interrupt();//interrupt async thread
    }
}

class HelloThread extends Thread{

    public void run() {
        int n = 0;
        while (! isInterrupted()) {
            n ++;
            System.out.println(n + " hello!");
            try{
                Thread.sleep(100);
            }catch (InterruptedException ex){
                break;
            }
        }
    }

}