package com.example.test;

import com.example.pattern.dynamicproxy.DataServiceImplA;
import com.example.pattern.dynamicproxy.IDataService;
import com.example.pattern.dynamicproxy.ProxyInvocationHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DynamicProxyTests {

    public DynamicProxyTests(){

    }

    @Test
    public void test(){

        IDataService service = new DataServiceImplA();
        Class class2 = service.getClass();
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler(service);

        Object proxy = proxyInvocationHandler.getDataServiceProxy();
        if(proxy instanceof  IDataService){
            IDataService serviceProxy = (IDataService) proxy;
            serviceProxy.update("test");//it will call proxy's invoke method which will invoke actual method
            //serviceProxy.delete();
        }

    }
}
