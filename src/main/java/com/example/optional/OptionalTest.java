package com.example.optional;

import java.util.Optional;

public class OptionalTest {

    public static void main(String... args){

        Integer value1 = null;
        Integer value2 = Integer.valueOf(10);

        Optional<Integer> a = Optional.ofNullable(value1);
        Optional<Integer> b = Optional.of(value2);

        if(!a.isPresent()){
            System.out.println("a is null");
            //a.orElse(Integer.valueOf(5));
            //System.out.println("reassign value:"+a.get().toString());
        }

        if(b.isPresent()){
            System.out.println(b.get().toString());
        }

    }
}
