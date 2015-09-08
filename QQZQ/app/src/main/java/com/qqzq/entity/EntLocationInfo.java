package com.qqzq.entity;

import android.text.TextUtils;

/**
 * Created by jie.xiao on 9/8/2015.
 */

//     {
//        "id": 372,
//        "code": "150000",
//        "province": "内蒙古自治区",
//        "city": "",
//        "district": "",
//        "parent": "1"
//      }
public class EntLocationInfo {
    private String id;
    private String code;
    private String province;
    private String city;
    private String district;
    private String parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getLocation() {
        if (!TextUtils.isEmpty(province)) {
            return province;
        } else if (!TextUtils.isEmpty(city)) {
            return city;
        } else {
            return district;
        }
    }
}
