package com.qqzq.entity;

/**
 * Created by jie.xiao on 15/10/26.
 */
public class EntSignupUser {

    /**
     * id : 8
     * userid : 12
     * username : abc
     * teamid : 1
     * actid : 1
     * signuptime : 1445707373000
     * cost : 1
     * hasattendance : true
     * islate : false
     * stat : 0
     * flag : 1
     * createdate : 1445707373000
     * updatedate : 1445707373000
     * userLogo : /upload/user_default_logo.png
     */

    private int id;
    private int userid;
    private String username;
    private int teamid;
    private int actid;
    private long signuptime;
    private int cost;
    private boolean hasattendance;
    private boolean islate;
    private String stat;
    private int flag;
    private long createdate;
    private long updatedate;
    private String userLogo;

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

    public void setSignuptime(long signuptime) {
        this.signuptime = signuptime;
    }

    public void setCost(int cost) {
        this.cost = cost;
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

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCreatedate(long createdate) {
        this.createdate = createdate;
    }

    public void setUpdatedate(long updatedate) {
        this.updatedate = updatedate;
    }

    public void setUserLogo(String userLogo) {
        this.userLogo = userLogo;
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

    public long getSignuptime() {
        return signuptime;
    }

    public int getCost() {
        return cost;
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

    public int getFlag() {
        return flag;
    }

    public long getCreatedate() {
        return createdate;
    }

    public long getUpdatedate() {
        return updatedate;
    }

    public String getUserLogo() {
        return userLogo;
    }
}
