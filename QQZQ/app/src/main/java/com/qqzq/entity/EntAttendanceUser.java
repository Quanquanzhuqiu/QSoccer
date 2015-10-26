package com.qqzq.entity;

/**
 * Created by jie.xiao on 15/10/27.
 */
public class EntAttendanceUser {


    /**
     * id : 0
     * userid : 0
     * username : string
     * teamid : 0
     * actid : 0
     * signuptime : 2015-10-26T11:41:47.671Z
     * hasattendance : true
     * islate : true
     * stat : string
     * flag : string
     * createdate : 2015-10-26T11:41:47.671Z
     * updatedate : 2015-10-26T11:41:47.671Z
     */

    private int id;
    private int userid;
    private String username;
    private int teamid;
    private int actid;
    private String signuptime;
    private boolean hasattendance;
    private boolean islate;
    private String stat;
    private String flag;
    private String createdate;
    private String updatedate;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public void setActid(int actid) {
        this.actid = actid;
    }

    public void setSignuptime(String signuptime) {
        this.signuptime = signuptime;
    }

    public void setHasattendance(boolean hasattendance) {
        this.hasattendance = hasattendance;
    }

    public void setIslate(boolean islate) {
        this.islate = islate;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public int getTeamid() {
        return teamid;
    }

    public int getActid() {
        return actid;
    }

    public String getSignuptime() {
        return signuptime;
    }

    public boolean isHasattendance() {
        return hasattendance;
    }

    public boolean isIslate() {
        return islate;
    }

    public String getStat() {
        return stat;
    }

    public String getFlag() {
        return flag;
    }

    public String getCreatedate() {
        return createdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }
}
