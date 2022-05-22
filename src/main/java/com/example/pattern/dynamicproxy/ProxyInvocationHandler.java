package com.example.pattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {

    /*
    //proxy for specify interface
    private IDataService service;

    public ProxyInvocationHandler(IDataService iDataService){
        this.service = iDataService;
    }
    */

    //proxy for ALL interface/class
    private Object service;

    public ProxyInvocationHandler(Object service){
        this.service = service;
    }


    public Object getDataServiceProxy(){

        Class<?> currentClass = getClass();// ProxyInvocationHandler
        ClassLoader classLoader = currentClass.getClassLoader(); // ClassLoaders$AppClassLoader@667
        Class<?> serviceClass = service.getClass(); //Class implements IDataService, e.g. DataServiceImplA
        Class<?>[] interfaces = serviceClass.getInterfaces();// return interfaces which are implemented by class2
        Object obj = Proxy.newProxyInstance(classLoader, interfaces, this);//Proxy object includes IDataService
        return obj;
    }

    /*
    * Developer can add customer business logic before/after DataService's method
    * */
    @Override
    //Object proxy is returned from getDataServiceProxy();
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = method.invoke(service,args);//invoke iDataService's method and return value of iDataService's method, e.g. return value of update("test")
        Class<?> proxyClass = proxy.getClass();//jdk.proxy3.$Proxy81
        String proxyName = proxyClass.getName();//jdk.proxy3.$Proxy81
        String methodName = method.getName();//IDataService's method, e.g. update("test")

        System.out.println(proxyName+"--------- proxy execute "+methodName+" return "+result);

        return result;
    }
}
