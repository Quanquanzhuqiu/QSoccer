package com.qqzq.entity;

/**
 * Created by jie.xiao on 15/10/13.
 */
public class EntPasswordModify {

    /**
     * subject : string
     * password : string
     * verifyCode : string
     */

    private String subject;
    private String password;
    private String verifyCode;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getSubject() {
        return subject;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
