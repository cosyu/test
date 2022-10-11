package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

//it is used to set AOP for specify method/class, e.g. check role for the method
@Aspect
@Component
public class AccessControlAspect {

    @Pointcut("execution(public * *(..)) && within(com.example..*)")//for all public method in the package
    public void publicMethod() {
    }

    @Pointcut("@annotation(com.example.aspect.HasAnyRole)")//for method with HasAnyRole annotation
    public void methodHasAnyRolePointcut() {
    }

    //it will be executed after preHandle of Interceptor(please refer to WebInterceptor)
    //specify point cuts, it will be executed before the methods with these point cuts
    @Before("publicMethod() && methodHasAnyRolePointcut()")
    public void preCheckByMethod(JoinPoint joinPoint) {
        System.out.println("preCheckByMethod..");
        Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();

        if(method.isAnnotationPresent(HasAnyRole.class)){
            HasAnyRole hasAnyRole = (HasAnyRole) method.getAnnotation(HasAnyRole.class);
            System.out.println("Role value:" + hasAnyRole.value());
        }

    }

    /*
    //if add around, before && after will not be executed
    //it will be executed after preHandle of Interceptor(please refer to WebInterceptor)
    //it will be executed before the methods with specify point cuts
    @Around("publicMethod() && methodHasAnyRolePointcut()")
    public void aroundCheckByMethod(JoinPoint joinPoint) {
        System.out.println("aroundCheckByMethod..");
    }
    */

    //it is executed after before
    @After("publicMethod() && methodHasAnyRolePointcut()")
    public void postCheckByMethod(JoinPoint joinPoint) {
        System.out.println("postCheckByMethod..");
    }




}
