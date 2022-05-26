package com.example.thread;

public class JoinTest {

    public static void main(String[] args) throws InterruptedException{

        Thread t = new Thread(){
            public void run(){
                try{
                    Thread.sleep(5000);
                    System.out.println("hello,status is:"+Thread.currentThread().getState());
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        };

        System.out.println("start");
        t.start();
        t.join();//join method make main thread wait for async method complete
        System.out.println("end");
    }
}
