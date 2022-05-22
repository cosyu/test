package com.example.pattern.abstractfactory;

public class BMW {

    private String brand;
    private String type;

    public BMW(){
        this.brand = "BMW";
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        return String.format("brand: %s , type: %s",brand,type);
    }
}
