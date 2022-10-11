package com.example.annotation.builder;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Child extends Parent{

    private String name;
    private int age;

    @Builder// customer builder for extended fields of super class
    public Child(String parentName, String name, int age) {
        super(parentName);
        this.name = name;
        this.age = age;
    }

}
