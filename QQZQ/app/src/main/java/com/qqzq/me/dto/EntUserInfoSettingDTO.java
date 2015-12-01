package com.qqzq.me.dto;

import java.io.Serializable;

/**
 * Created by syouketu on 15/11/24.
 */
public class EntUserInfoSettingDTO implements Serializable {


    /**
     * username : 13811111111
     * nickname : 阿凡达 001
     * sex : 0
     * area : 前锋
     * city : 3333
     * province : 44444
     * autograph : sdafsadfsadf
     * avatar : sdafsadfsadf
     * age : "31"
     */

    private String username;
    private String nickname;
    private String sex;
    private String area;
    private int city;
    private int province;
    private String autograph;
    private String avatar;
    private String age;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSex() {
        return sex;
    }

    public String getArea() {
        return area;
    }

    public int getCity() {
        return city;
    }

    public int getProvince() {
        return province;
    }

    public String getAutograph() {
        return autograph;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
