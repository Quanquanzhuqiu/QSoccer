package com.qqzq.entity;

import java.util.Date;

/**
 * Created by jie.xiao on 15/10/12.
 */
public class EntTeamJoinInfo {
    /**
     * id : 0
     * userid : 0
     * username : string
     * usernickname : string
     * teamid : 0
     * joinway : 0
     * isleader : true
     * jointime : 2015-10-12T13:27:54.345Z
     * personalscore : 0
     * attendancecount : 0
     * stat : string
     * flag : string
     * createdate : 2015-10-12T13:27:54.345Z
     * updatedate : 2015-10-12T13:27:54.345Z
     * teamname : string
     */

    private int id;
    private String userid;
    private String username;
    private String usernickname;
    private int teamid;
    private int joinway;
    private boolean isleader;
    private Date jointime;
    private int personalscore;
    private int attendancecount;
    private String stat;
    private String flag;
    private Date createdate;
    private Date updatedate;
    private String teamname;

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(String userid) {
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

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public int getId() {
        return id;
    }

    public String getUserid() {
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

    public int getJoinway() {
        return joinway;
    }

    public boolean getIsleader() {
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

    public String getStat() {
        return stat;
    }

    public String getFlag() {
        return flag;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public String getTeamname() {
        return teamname;
    }
}
