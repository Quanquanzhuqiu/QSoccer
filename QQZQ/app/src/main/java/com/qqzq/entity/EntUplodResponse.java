package com.qqzq.entity;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class EntUplodResponse {

    private String code;
    private String innerMessage;
    private String customMessage;
    private String moreInfo;
    private String location;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInnerMessage() {
        return innerMessage;
    }

    public void setInnerMessage(String innerMessage) {
        this.innerMessage = innerMessage;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
