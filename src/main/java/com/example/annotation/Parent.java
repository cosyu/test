package com.example.annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
public class Parent implements Serializable {

    protected String parentName;
}
