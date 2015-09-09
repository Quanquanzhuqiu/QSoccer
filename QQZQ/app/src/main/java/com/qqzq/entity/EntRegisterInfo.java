package com.qqzq.entity;

/**
 * Created by jie.xiao on 15/9/9.
 */

/*
        {
        "username": "string",
        "password": "string",
        "verifyCode": "string",
        "city": 0,
        "province": 0
        }
*/
public class EntRegisterInfo {
    private String username;
    private String password;
    private String verifyCode;
    private int city;
    private int province;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }
}
