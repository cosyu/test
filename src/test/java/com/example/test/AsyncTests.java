package com.example.test;

import com.example.async.AsyncClass;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

@SpringBootTest
public class AsyncTests {

    @Autowired
    private AsyncClass asyncClass;

    public AsyncTests(){

    }

    @Test
    public void test() throws Exception{

        System.out.println("--------start run ---------current thread:" + Thread.currentThread().getName());
        //asyncClass.asyncMethod();
        //System.out.println("--------run end---------current thread:"+Thread.currentThread().getName());

        Future<String> future = asyncClass.asyncMethod2();
        while (true) { ///這裡使用了迴圈判斷，等待獲取結果資訊
            if (future.isDone()) { //判斷是否執行完畢
                System.out.println("Result from asynchronous process - "  + future.get());
                break;
            }
            //System.out.println("Continue doing something else. ");
            //Thread.sleep(1000);
        }

        System.out.println("--------run end---------current thread:"+Thread.currentThread().getName());
    }

    @Test
    public void test2() throws Exception{

        System.out.println("--------start run ---------current thread:" + Thread.currentThread().getName());

        try{
            asyncClass.asyncMethod();
        }catch (Exception ex){
            //caller can not try catch the exception threw by async method without return value, for the async method
            //without return value, the exception will be handled by
            //AsyncUncaughtExceptionHandler which is defined in AsyncConfig class
            System.out.println("------exception from async method, error: " + ex.getMessage());
        }

        System.out.println("--------run end---------current thread:"+Thread.currentThread().getName());
    }

    @Test
    public void test3() throws Exception{

        System.out.println("--------start run ---------current thread:" + Thread.currentThread().getName());

        Future<String> future = asyncClass.asyncMethod2();

        //for async method with return value, try catch future.get() to catch the exception
        // which is thrown by async method in the main thread
        if(future != null){
            try{
                future.get();
            }catch (Exception ex){
                System.out.println("catch exception from async method with return value:" + ex.getMessage());
            }
        }

        System.out.println("--------run end---------current thread:"+Thread.currentThread().getName());
    }

    @Test
    public void test4() throws Exception{

        System.out.println("--------start run ---------current thread:" + Thread.currentThread().getName());
        //the async should be put in another class, otherwise,
        //this method will run in main thread, @Async is not working
        asyncMethod();
        System.out.println("--------run end---------current thread:"+Thread.currentThread().getName());

    }

    @Async("threadPool")
    public void asyncMethod() throws Exception{

        System.out.println("-----"+Thread.currentThread().getName()+ "  asyncMethod is start");

        try{
            Thread.sleep(10000);
        }catch (Exception ex){
            System.out.println("------run asyncMethod error:" + ex.getMessage());
        }

        System.out.println("-----"+Thread.currentThread().getName()+ "  asyncMethod is end");

    }
}
