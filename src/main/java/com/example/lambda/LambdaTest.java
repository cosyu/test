package com.example.lambda;

import com.google.common.collect.Lists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LambdaTest {

    public static void main(String... args){

        String[] externalUsertype = {"RP","PERSON","EMPLOYEE"};
        List<String> externalUsertypeList = Arrays.stream(externalUsertype).toList();
        List<String> list = Lists.newArrayList("1","2","3");
        list.forEach(s->{
            System.out.println(s);
        });
        externalUsertypeList.forEach(s->{
            System.out.println(s);
        });
        List<String> list2 = list.stream().filter(s->Integer.valueOf(s)<3).collect(Collectors.toList());
        list2.forEach(s->{
            System.out.println(s);
        });
        double average = list.stream().filter(s->Integer.valueOf(s)<3).mapToInt(s->Integer.valueOf(s)).average().getAsDouble();
        System.out.println(average);

        Map<String,String> map = Map.of("a","1","b","2","c","3");
        map.forEach((String k,String v)->{ //k,v will be string automatically,String can be removed
            System.out.println(k+"-"+v);
        });

        //implements interface which has only one method with no argument
        Runnable r1 = () -> {
            System.out.println("runnable");
        };
        new Thread(r1).start();

        new Thread(
                ()->{
                    System.out.println("hello");
                    System.out.println("world");
                }
        ).start();

        //implement method with arguments
        List<String> strList = Arrays.asList("I", "love", "you", "too");
        Collections.sort(strList,(String s1, String s2)->{ //s1,s2 will be string automatically,String can be removed
            if(s1 == null){
                return  -1;
            }
            if(s2 == null){
                return 1;
            }
            return s1.length()-s2.length();
        });
        strList.forEach(s->{
            System.out.println(s);
        });


        ActionListener listener = (ActionEvent event2) -> { //implement ActionListener interface
            System.out.println("button clicked");
        };
    }


}
