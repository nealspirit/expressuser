package com.example.express_user;

import org.litepal.crud.DataSupport;

public class Delivery extends DataSupport{
    private String deliveryNum;
    private String location;
    private String phoneNum;
    private String RandomCode;

    public String getdeliveryNum() {
        return deliveryNum;
    }

    public void setdeliveryNum(String deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getRandomCode() {
        return RandomCode;
    }

    public void setRandomCode(String randomCode) {
        RandomCode = randomCode;
    }
}
