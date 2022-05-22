package com.example.test;

import com.example.pattern.abstractfactory.*;
import com.example.pattern.factory.Factory;
import com.example.pattern.factory.FrenchFries;
import com.example.pattern.factory.FrenchFriesFactory;
import com.example.pattern.factory.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FactoryTests {

    public FactoryTests(){

    }

    @Test
    public void test(){

        /*
        * To get product, client only need to access factory
        * */
        Factory factory = new FrenchFriesFactory();
        Product product = factory.getProduct();
        product.describe();

        product = factory.getProduct("no salt");
        product.describe();

    }
}
