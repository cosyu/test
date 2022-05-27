package com.example.thread;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest {

    public static void main(String[] args) throws Exception{

        System.out.println("Main thread: " +Thread.currentThread().getName()+ " start");
        //create async thread for fetchPrice method
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice);

        //if execute success
        cf.thenAccept((result)->{
            System.out.println("price: " + result);
        });

        //if fail to execute
        cf.exceptionally((e)->{
            e.printStackTrace();
            return null;
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(2000);
        System.out.println("Main thread: " +Thread.currentThread().getName()+ " end");
    }

    private static Double fetchPrice(){

        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " start");
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        if(Math.random()<0.3){
            throw new RuntimeException("fetch price failed!");
        }

        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " end");
        return 5+Math.random()*20;
    }
}
