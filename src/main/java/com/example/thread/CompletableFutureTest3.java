package com.example.thread;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFutureTest3 {

    public static void main(String[] args) throws Exception{

        System.out.println("Main Thread: " +Thread.currentThread().getName()+ " start");
        //first async task
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(()->{
            return queryCode("Tencent","https://finance.sina.com.cn/code/");
        });

        //second async task, the thread will be different from first async task
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(()->{
            return queryCode("Tencent","https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina,cfQueryFrom163);

        //use the async thread of cfQueryFromSina
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code)->{
            return fetPrice((String)code,"https://finance.sina.com.cn/price/");
        });

        //use the async thread of cfQueryFrom163
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code)->{
            return fetPrice((String)code,"https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina,cfFetchFrom163);

        cfFetch.thenAccept((result)->{
            System.out.println("price: " + result);
        });

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(6000);
        System.out.println("Main Thread: " +Thread.currentThread().getName()+ " end");
    }

    private static String queryCode(String name, String url){

        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " start");
        System.out.println("query code from " + url);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " end");
        return "601857";
    }

    private static Double fetPrice(String code,String url){

        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " start");
        System.out.println("fetch price from " + url);
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("Async Thread: " +Thread.currentThread().getName()+ " end");
        return 5+Math.random()*20;
    }
}
