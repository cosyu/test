package com.example.async;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Bean("threadPool")
    @Override
    public Executor getAsyncExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-queue-thread-%d").build();

        //defined the task executor for async method
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //ThreadPoolTaskExecutor->AsyncListenableTaskExecutor->AsyncTaskExecutor->TaskExecutor->Executor

        // 线程池维护线程的最少数量
        executor.setCorePoolSize(5);
        // 线程池维护线程的最大数量
        executor.setMaxPoolSize(10);
        // 缓存队列
        executor.setQueueCapacity(25);
        //线程名
        executor.setThreadFactory(namedThreadFactory);
        //executor.setThreadNamePrefix("async---");
        // 线程池初始化
        executor.initialize();
        return executor;
    }

    //Exception handler for async method without return value.
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}
