package com.example.optional;

import com.example.domain.User;

import java.util.Optional;

public class OptionalTest {

    public static void main(String... args) throws Exception{

        //TODO https://blog.csdn.net/qq_35634181/article/details/101109300
        Integer value1 = null;
        Integer value2 = Integer.valueOf(10);

        Optional<Integer> a = Optional.ofNullable(value1);
        Optional<Integer> b = Optional.of(value2);

        if(!a.isPresent()){//a is Optional empty
            System.out.println("a is null");
            //a.orElse(Integer.valueOf(5));
            //System.out.println("reassign value:"+a.get().toString());
        }

        if(b.isPresent()){
            System.out.println(b.get().toString());
        }

        User user = new User();
        User user2 = null;

        //get the property
        String res = Optional.ofNullable(user).map(User::getName).orElse("default name");
        System.out.println(res);

        String res2 = Optional.ofNullable(user).map(User::getName).orElse(null);//:: is used to execute method, e.g. getName,setName
        System.out.println(res2);

        //it will be default name as the user2 is null, it will be Optional empty
        String res3 = Optional.ofNullable(user2).map(User::getName).orElse("default name");
        System.out.println(res3);

        //it throws exception if user is null
        Optional.of(user).orElseThrow(() -> new Exception("user is null"));

        //it throws exception if username is null
        Optional.of(user).map(User::getName).orElseThrow(()->new Exception("user name is null"));

        //it throws exception if user2 is null
        Optional.ofNullable(user2).orElseThrow(() -> new Exception("user2 is null"));

    }
}
