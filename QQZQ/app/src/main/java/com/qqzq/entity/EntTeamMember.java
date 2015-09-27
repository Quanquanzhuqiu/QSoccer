package com.qqzq.entity;

import java.util.Date;

/**
 * Created by jie.xiao on 15/9/27.
 */
public class EntTeamMember {
    private int id;
    private int userid;
    private String username;
    private String usernickname;
    private int teamid;
    private int joinway;
    private boolean isleader;
    private Date jointime;
    private int personalbalance;
    private int personalscore;
    private int attendancecount;
    private int attendancerate;
    private int stat;
    private int flag;
    private Date createdate;
    private Date updatedate;
    private String teamname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernickname() {
        return usernickname;
    }

    public void setUsernickname(String usernickname) {
        this.usernickname = usernickname;
    }

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public int getJoinway() {
        return joinway;
    }

    public void setJoinway(int joinway) {
        this.joinway = joinway;
    }

    public boolean isleader() {
        return isleader;
    }

    public void setIsleader(boolean isleader) {
        this.isleader = isleader;
    }

    public Date getJointime() {
        return jointime;
    }

    public void setJointime(Date jointime) {
        this.jointime = jointime;
    }

    public int getPersonalbalance() {
        return personalbalance;
    }

    public void setPersonalbalance(int personalbalance) {
        this.personalbalance = personalbalance;
    }

    public int getAttendancecount() {
        return attendancecount;
    }

    public void setAttendancecount(int attendancecount) {
        this.attendancecount = attendancecount;
    }

    public int getAttendancerate() {
        return attendancerate;
    }

    public void setAttendancerate(int attendancerate) {
        this.attendancerate = attendancerate;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public int getPersonalscore() {
        return personalscore;
    }

    public void setPersonalscore(int personalscore) {
        this.personalscore = personalscore;
    }
}
