package com.example.test;

import com.example.pattern.factory.Factory;
import com.example.pattern.factory.FrenchFriesFactory;
import com.example.pattern.factory.Product;
import com.example.pattern.proxy.Agent;
import com.example.pattern.proxy.IBuyHouse;
import com.example.pattern.proxy.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProxyTests {

    public ProxyTests(){

    }

    @Test
    public void test(){

        /*
        * Agent make action for tenant
        * */
        IBuyHouse tenant = new Tenant();
        IBuyHouse agent = new Agent(tenant);

        agent.findHouse();
        agent.finish();

    }
}
