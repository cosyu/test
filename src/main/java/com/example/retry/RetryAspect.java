package com.example.retry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RetryAspect {

    @Pointcut("execution(public * com.example..*.*(..)) && " + "@annotation(com.example.retry.MyRetryable)")
    public void myPointcut() {
    }

    @Around("myPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        Method method = getCurrentMethod(point);
        MyRetryable retryable = method.getAnnotation(MyRetryable.class);

        //1. 最大次数判断
        int maxAttempts = retryable.maxAttempts();
        if (maxAttempts <= 1) {
            return point.proceed();
        }

        //2. 异常处理
        int times = 0;
        final Class<? extends Throwable> exceptionClass = retryable.value();
        while (times < maxAttempts) {
            try {
                return point.proceed();
            } catch (Throwable e) {
                times++;

                // 超过最大重试次数 or 不属于当前处理异常
                if (times >= maxAttempts ||
                        !e.getClass().isAssignableFrom(exceptionClass)) {
                    throw new Throwable(e);
                }
            }
        }

        return null;
    }

    private Method getCurrentMethod(ProceedingJoinPoint point) {
        try {
            MethodSignature msig = (MethodSignature)point.getSignature();
            return point.getTarget().getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

}
