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

    }

}
