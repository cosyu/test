package com.example.thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NotifyTest {

    public static void main(String[] args){

        TaskQueue taskQueue = new TaskQueue();
        List<Thread> taskList = new ArrayList<Thread>();

        //new 5 thread and start 5 thread to get task and trigger wait
        for(int i=0;i<5;i++){
            Thread t = new Thread(){
              public void run(){
                  while (true){
                      try {
                          String s = taskQueue.getTask();
                          System.out.println("execute task: " + s);
                      } catch (InterruptedException e) {
                          return;
                      }
                  }
              }
            };

            t.start();
            taskList.add(t);
        }

        //another new thread, add task and trigger notify
        Thread addThread = new Thread(){
          public void run(){
              for(int i=0;i<10;i++){
                  String s = "t-"+Math.random();
                  System.out.println("add task:"+s);
                  taskQueue.addTask(s);
                  try{
                      Thread.sleep(1000);
                  }catch (InterruptedException ex){
                        ex.printStackTrace();
                  }
              }
          }
        };
        addThread.start();

        try{
            addThread.join();
        }catch (InterruptedException ex){
            System.out.println("InterruptedException at addThread;"+ex.getMessage());
        }
        try{
            Thread.sleep(100);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        for(Thread task : taskList){
            task.interrupt();
        }
        System.out.println("end");
    }

}

class TaskQueue{
    Queue<String> queue = new LinkedList<String>();

    public synchronized void addTask(String s){
        this.queue.add(s);
        this.notifyAll();//notify all waiting thread
    }

    public synchronized String getTask() throws InterruptedException{
        while (queue.isEmpty()){
            //wait, release lock for other thread, when the thread is notified,
            //it will get lock again
            this.wait();
        }
        return queue.remove();
    }
}

