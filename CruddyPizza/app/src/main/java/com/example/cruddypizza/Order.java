package com.example.cruddypizza;

import java.util.Date;

public class Order {
    public long id;
    public int size;
    public int topping1;
    public int topping2;
    public int topping3;
    public String fName;
    public String lName;
    public String address;
    public int phone;
    public Date orderDateTime;

    public Order(int size, String fName, String lName, String address, int phone) {
        this.size = size;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.phone = phone;
    }

    public Order(int size, int topping1, String fName, String lName, String address, int phone) {
        this.size = size;
        this.topping1 = topping1;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.phone = phone;
    }

    public Order(int size, int topping1, int topping2, String fName, String lName, String address, int phone) {
        this.size = size;
        this.topping1 = topping1;
        this.topping2 = topping2;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.phone = phone;
    }

    public Order(int size, int topping1, int topping2, int topping3, String fName, String lName, String address, int phone) {
        this.size = size;
        this.topping1 = topping1;
        this.topping2 = topping2;
        this.topping3 = topping3;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTopping1() {
        return topping1;
    }

    public void setTopping1(int topping1) {
        this.topping1 = topping1;
    }

    public int getTopping2() {
        return topping2;
    }

    public void setTopping2(int topping2) {
        this.topping2 = topping2;
    }

    public int getTopping3() {
        return topping3;
    }

    public void setTopping3(int topping3) {
        this.topping3 = topping3;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
