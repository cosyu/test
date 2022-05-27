package com.example.thread;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureTest2 {

    public static void main(String[] args) throws Exception{

        System.out.println("Main Thread: " +Thread.currentThread().getName()+ " start");
        //async task
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(()->{
            return queryCode("Tencent");
        });

        //cfQuery成功后继续执行下一个任务, it will use the same async thread
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code)->{
            return fetPrice(code);
        });

        // cfFetch成功后打印结果:
        cfFetch.thenAccept((result)->{
            System.out.println("price:" + result);
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(4000);
        System.out.println("Main Thread: " +Thread.currentThread().getName()+ " end");
    }

    private static String queryCode(String name){

        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " start");
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " end");
        return "601857";
    }

    private static Double fetPrice(String code){

        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " start");
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " end");
        return 5+Math.random()*20;
    }
}
