package com.qqzq.user.dto;

/**
 * Created by jie.xiao on 12/1/2015.
 */
public class EntLoginDTO {

    /**
     * username : string
     * password : string
     * rememberme : true
     */

    private String username;
    private String password;
    private boolean rememberme;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRememberme(boolean rememberme) {
        this.rememberme = rememberme;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isRememberme() {
        return rememberme;
    }
}
