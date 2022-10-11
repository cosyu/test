package com.example.annotation.builder;

public class BuilderTest {

    public static void main(String... args) throws Exception{

        Child.ChildBuilder builder = Child.builder();
        //parentName is supper class' property
        Child c = builder.name("Season").age(7).parentName("Jason").build();
        System.out.println(c.toString());

    }

}
