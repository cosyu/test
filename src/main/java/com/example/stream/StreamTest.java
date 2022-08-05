package com.example.stream;

import com.example.test.User;
import com.google.common.collect.Lists;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String... args){

        String[] externalUsertype = {"RP","PERSON","EMPLOYEE"};
        List<String> externalUsertypeList = Arrays.stream(externalUsertype).toList();
        List<String> list = Lists.newArrayList("1","2","3");
        List<User> userList = new ArrayList<User>();
        userList.add(User.builder().id(2).name("admin2").password("123").build());
        userList.add(User.builder().id(4).name("user2").password("123").build());
        userList.add(User.builder().id(1).name("admin1").password("123").build());
        userList.add(User.builder().id(3).name("user1").password("123").build());
        userList.add(User.builder().id(5).name("user3").password("123").build());
        userList.add(null);
        System.out.println("user list size:"+userList.size());

        //filter is used to filter object for the list
        //List<String> list2 = list.stream().filter(s->Integer.valueOf(s)<3).collect(Collectors.toList());
        List<String> list2 = list.stream().filter(s->Integer.valueOf(s)<3).toList();
        list2.forEach(s->{
            System.out.println(s);
        });

        List<User> userList1 = userList.stream().filter(Objects::nonNull).filter(user1 -> user1.getName().contains("admin")).toList();
        userList1.forEach(user->{
            System.out.println(user.getName());
        });

        //map is used to get property of object
        List<Integer> userIdList = userList.stream().filter(Objects::nonNull).map(User::getId).toList();//:: is used to execute method,e.g. getId,setId
        userIdList.forEach(s->{
            System.out.println(s);
        });

        double average = list.stream().mapToInt(s->Integer.valueOf(s)).average().getAsDouble();
        System.out.println(average);

        //any match
        boolean containUser = userList.stream().anyMatch(user1->user1.getName().contains("user"));
        System.out.println(containUser);

        //sort, compare
        List<User> userList3 = userList.stream().filter(Objects::nonNull)
                .sorted(Comparator.comparing(User::getId,Comparator.reverseOrder()).thenComparing(User::getName)).toList();//compare by id
        userList3.forEach(s->{
            System.out.println(s);
        });



    }


}
