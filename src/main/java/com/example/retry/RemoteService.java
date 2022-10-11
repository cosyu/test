package com.example.retry;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RemoteService {

    /**
     * 调用方法
     */
    @Retryable(value = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 2))
    public void call() {
        System.out.println("call remote service "+new Date());
        throw new RuntimeException("RPC Exception");
    }

    /**
     * recover 机制
     * @param e 异常
     * 全部重试失败，则调用 recover() 方法
     */
    @Recover
    public void recover(RuntimeException e) {
        System.out.println("Start do recover things....");
        System.out.println("We meet ex: "+ e.getMessage());
    }
}
