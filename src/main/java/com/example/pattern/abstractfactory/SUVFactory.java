package com.example.pattern.abstractfactory;

//concrete factory to create concrete product
public class SUVFactory extends AbstractFactory{

    @Override
    public Audi createAudi() {
        return new AudiSUV();
    }

    @Override
    public BMW createBMW() {
        return new BMWSUV();
    }
}
