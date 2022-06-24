package com.example.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;


//This Class must be created and maintained by Spring, otherwise, it will not create thread for async method
@Component
public class AsyncClass {

    //it will use TaskExecutor which is  defined in AsyncConfig class
    //the thread will be put in the pool
    @Async("threadPool")
    //@Transactional
    //if async method needs transactional, declare @Transactional to the method,
    //@Transactional declared in main thread is NOT working for the async method
    public void asyncMethod() throws Exception{

        System.out.println("-----"+Thread.currentThread().getName()+ "  asyncMethod is start");

        try{
            //Thread.sleep(10000);
//            if(true){
//                throw new Exception("test throw exception from async method");
//            }
        }catch (Exception ex){
            System.out.println("------run asyncMethod error:" + ex.getMessage());
            throw ex;
        }

        System.out.println("-----"+Thread.currentThread().getName()+ "  asyncMethod is end");

    }

    //it will use TaskExecutor which is  defined in AsyncConfig class
    //the thread will be put in the pool
    @Async("threadPool")
    public Future<String> asyncMethod2() throws Exception{

        System.out.println("-----"+Thread.currentThread().getName()+ "  asyncMethod is start");
        try{
            //Thread.sleep(5000);
//            if(true){
//                throw new Exception("test throw exception from async method");
//            }
            System.out.println("-----"+Thread.currentThread().getName()+ "  asyncMethod is end");
            return new AsyncResult<String>("Hello World!");//return Future<T>
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            throw ex;
        }

    }
}
