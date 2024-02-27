package com.example.canteenmanagement;

public class Model_Contact {
    String Name;
    String MoNumber;

    public Model_Contact() {
    }

    public Model_Contact(String name, String moNumber) {
        Name = name;
        MoNumber = moNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMoNumber() {
        return MoNumber;
    }

    public void setMoNumber(String moNumber) {
        MoNumber = moNumber;
    }
}
