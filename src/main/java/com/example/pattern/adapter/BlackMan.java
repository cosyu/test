package com.example.pattern.adapter;

public class BlackMan {

    private String name;

    public BlackMan(String name){
        this.name = name;
    }

    public void helloEnglish(){
        System.out.println("yo~ what's up!! niga~");
    }

    public void selfIntroEnglish(){
        System.out.println("hello, I living in taipei. " +
                "my name is " + this.name + ".");
    }

    public String getName() {
        return name;
    }
}
