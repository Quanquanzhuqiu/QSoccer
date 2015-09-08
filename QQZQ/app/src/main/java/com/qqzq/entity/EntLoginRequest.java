package com.qqzq.entity;

/**
 * Created by jie.xiao on 9/8/2015.
 */

//  {
//      "username": "string",
//      "password": "string",
//      "rememberme": true
//  }
public class EntLoginRequest {

    private String username;
    private String password;
    private boolean rememberme;

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

    public boolean isRememberme() {
        return rememberme;
    }

    public void setRememberme(boolean rememberme) {
        this.rememberme = rememberme;
    }
}
