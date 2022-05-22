package com.example.async;

import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

//A customer wrapper for AsyncTaskExecutor, it allows developer to handle exception for async method
//There is no annotation to indicate a class is AsyncTaskExecutor, it needs to defined it in XML, not recommend this approach
public class CustomerAsyncTaskExecutor implements AsyncTaskExecutor {

    private AsyncTaskExecutor asyncTaskExecutor;

    public CustomerAsyncTaskExecutor() {
        System.out.println("---create ExceptionHandlerAsyncTaskExecutor---");
    }

    public CustomerAsyncTaskExecutor(AsyncTaskExecutor asyncTaskExecutor) {
        this.asyncTaskExecutor = asyncTaskExecutor;
    }

    @Override
    public void execute(Runnable task) {
        asyncTaskExecutor.execute(createRunnable(task));
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        asyncTaskExecutor.execute(createRunnable(task),startTimeout);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return asyncTaskExecutor.submit(createRunnable(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return asyncTaskExecutor.submit(createCallable(task));
    }

    private <T> Callable<T> createCallable(final Callable<T> task){

        Callable callable = new Callable() {
            @Override
            public T call() throws Exception {
                try{
                    return task.call();
                }catch (Exception ex){
                    //handle exception for async method
                    handleException(ex);
                    throw ex;
                }
            }
        };

        return callable;
    }

    private Runnable createRunnable(final Runnable task){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    task.run();
                }catch (Exception ex){
                    //handle exception for async method
                    handleException(ex);
                }
            }
        };

        return runnable;
    }

    private void handleException(Exception ex){
        System.out.println("=======Error during @Async execute: " + ex.getMessage());
    }

}
