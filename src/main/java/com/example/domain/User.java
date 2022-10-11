package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    //validation
    @Pattern(regexp = "^[_'.@A-Za-z0-9-]*$",message = "name must be letter or number, min is 5, max is 40")
    @Size(min = 5, max = 40)
    private String name;

    @Pattern(regexp = "^[_'.@A-Za-z0-9-]*$",message = "password must be letter or number")
    private String password;

    /*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    */
}
