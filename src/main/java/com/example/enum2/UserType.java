package com.example.enum2;

public enum UserType {

    PERSON("PERSON"),//enum object, call the constructor
    //OTHER, it is compiled error as constructor needs string parameter
    RP("RP"),
    EMPLOYEE("EMPLOYEE"),
    PGA("PGA"),
    OPERATOR("OPERATOR"),
    ADMINISTRATOR("ADMINISTRATOR"),
    OFFICER("OFFICER"),
    ADMIN_SUPPORT2("ADMIN_SUPPORT");

    private final String type;

    private UserType(String type){ // constructor of enum
        this.type = type;
    }

    public String getType(){
        return this.type;
    }

}
