package com.example.annotation;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Child extends Parent{

    private String name;
    private int age;
}
