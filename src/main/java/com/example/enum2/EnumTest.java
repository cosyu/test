package com.example.enum2;

import com.example.annotation.Child;

public class EnumTest {


    public static void main(String... args) throws Exception{

        Color color = Color.RED;

        switch (color){
            case RED:
                System.out.println("red");
                break;
            case YELLOW:
                System.out.println("yellow");
                break;
        }

        UserType userType = UserType.ADMIN_SUPPORT2;

        //type is the parameter of constructor, e.g. ADMIN_SUPPORT2("ADMIN_SUPPORT")
        //name is enum name, e.g. ADMIN_SUPPORT2
        System.out.println(userType.getType()+"--"+userType.name());

        if(UserType.ADMIN_SUPPORT2.equals(userType)){
            System.out.println("enum match");
        }

        if(UserType.ADMIN_SUPPORT2.name().equals("ADMIN_SUPPORT2")){
            System.out.println("enum name match");
        }

    }

}
