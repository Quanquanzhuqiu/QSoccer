package com.qqzq.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jie.xiao on 15/10/26.
 */
public class EntAttendanceInfo {

    private List<EntAttendanceUser> attendances;


    public EntAttendanceInfo() {
        attendances = new ArrayList<>();
    }

    public List<EntAttendanceUser> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<EntAttendanceUser> attendances) {
        this.attendances = attendances;
    }

}
