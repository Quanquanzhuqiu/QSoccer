package com.qqzq.user.dto;

import java.io.Serializable;

/**
 * Created by syouketu on 15/11/26.
 */
public class EntChangePasswordDTO implements Serializable{



    /**
     * password : string
     * verifyCode : string
     */

    private String password;
    private String verifyCode;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
