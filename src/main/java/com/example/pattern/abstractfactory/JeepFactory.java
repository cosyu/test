package com.example.pattern.abstractfactory;

//concrete factory to create concrete product
public class JeepFactory extends AbstractFactory{

    @Override
    public Audi createAudi() {
        return new AudiJeep();
    }

    @Override
    public BMW createBMW() {
        return new BMWJeep();
    }
}
