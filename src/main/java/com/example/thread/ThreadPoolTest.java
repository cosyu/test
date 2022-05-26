package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

    public static void main(String[] args){

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        //ExecutorService->Executor
        for(int i=0;i<6;i++){
            executorService.submit(new Task(""+i));
        }
        executorService.shutdown();
    }
}

class Task implements Runnable{

    private final String name;

    public Task(String name){
        this.name = name;
    }

    public void run(){
        System.out.println("start task " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end task " + name);
    }

}
