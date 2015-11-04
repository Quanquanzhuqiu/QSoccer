package com.qqzq.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jie.xiao on 15/10/27.
 */
public class EntTeamMember implements Serializable{

    /**
     * id : 12
     * userid : 3
     * username : 13811111112
     * usernickname : C
     * teamid : 11
     * teamname : ssssssss
     * joinway : 0
     * isleader : true
     * jointime : 1445937336000
     * personalbalance : 0
     * personalscore : 0
     * attendancecount : 0
     * attendancerate : 0
     * stat : 1
     * flag : 1
     * createdate : 1445937336000
     * updatedate : 1445937336000
     * userLogo : /upload/user_default_logo.png
     */

    private int id;
    private int userid;
    private String username;
    private String usernickname;
    private int teamid;
    private String teamname;
    private int joinway;
    private boolean isleader;
    private Date jointime;
    private float personalbalance;
    private int personalscore;
    private int attendancecount;
    private int attendancerate;
    private int stat;
    private int flag;
    private Date createdate;
    private Date updatedate;
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

    public void setUsernickname(String usernickname) {
        this.usernickname = usernickname;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void setJoinway(int joinway) {
        this.joinway = joinway;
    }

    public void setIsleader(boolean isleader) {
        this.isleader = isleader;
    }

    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }

    public void setPersonalscore(int personalscore) {
        this.personalscore = personalscore;
    }

    public void setAttendancecount(int attendancecount) {
        this.attendancecount = attendancecount;
    }

    public void setAttendancerate(int attendancerate) {
        this.attendancerate = attendancerate;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public void setUpdatedate(Date updatedate) {
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

    public String getUsernickname() {
        return usernickname;
    }

    public int getTeamid() {
        return teamid;
    }

    public String getTeamname() {
        return teamname;
    }

    public int getJoinway() {
        return joinway;
    }

    public boolean isIsleader() {
        return isleader;
    }

    public Date getJointime() {
        return jointime;
    }

    public int getPersonalscore() {
        return personalscore;
    }

    public int getAttendancecount() {
        return attendancecount;
    }

    public int getAttendancerate() {
        return attendancerate;
    }

    public int getStat() {
        return stat;
    }

    public int getFlag() {
        return flag;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public String getUserLogo() {
        return userLogo;
    }

    public boolean isleader() {
        return isleader;
    }

    public float getPersonalbalance() {
        return personalbalance;
    }

    public void setPersonalbalance(float personalbalance) {
        this.personalbalance = personalbalance;
    }
}
