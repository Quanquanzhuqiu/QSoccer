package com.qqzq.entity;

import java.util.Date;

/**
 * Created by jie.xiao on 15/8/31.
 */
public class EntTeamInfo {
    private int id;
    private String teamname;
    private String teamno;
    private int oftencity;
    private int oftendistinct;
    private String oftensoccerpernum;
    private int joinconfig;
    private String sumary;
    private Date establishdate;
    private String teamleadernm;
    private String teamleaderusrrnm;
    private String teamleaderqqid;
    private String teamlogo;
    private String stat;
    private String flag;
    private Date createdate;
    private Date updatedate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getTeamno() {
        return teamno;
    }

    public void setTeamno(String teamno) {
        this.teamno = teamno;
    }

    public int getOftencity() {
        return oftencity;
    }

    public void setOftencity(int oftencity) {
        this.oftencity = oftencity;
    }

    public int getOftendistinct() {
        return oftendistinct;
    }

    public void setOftendistinct(int oftendistinct) {
        this.oftendistinct = oftendistinct;
    }

    public String getOftensoccerpernum() {
        return oftensoccerpernum;
    }

    public void setOftensoccerpernum(String oftensoccerpernum) {
        this.oftensoccerpernum = oftensoccerpernum;
    }

    public int getJoinconfig() {
        return joinconfig;
    }

    public void setJoinconfig(int joinconfig) {
        this.joinconfig = joinconfig;
    }

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public Date getEstablishdate() {
        return establishdate;
    }

    public void setEstablishdate(Date establishdate) {
        this.establishdate = establishdate;
    }

    public String getTeamleadernm() {
        return teamleadernm;
    }

    public void setTeamleadernm(String teamleadernm) {
        this.teamleadernm = teamleadernm;
    }

    public String getTeamleaderusrrnm() {
        return teamleaderusrrnm;
    }

    public void setTeamleaderusrrnm(String teamleaderusrrnm) {
        this.teamleaderusrrnm = teamleaderusrrnm;
    }

    public String getTeamleaderqqid() {
        return teamleaderqqid;
    }

    public void setTeamleaderqqid(String teamleaderqqid) {
        this.teamleaderqqid = teamleaderqqid;
    }

    public String getTeamlogo() {
        return teamlogo;
    }

    public void setTeamlogo(String teamlogo) {
        this.teamlogo = teamlogo;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
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

    @Override
    public String toString() {
        return teamname;
    }
}
