package com.qqzq.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jie.xiao on 15/8/31.
 */
public class EntTeamInfoDTO implements Serializable{
    /**
     * id : 11
     * teamname : ssssssss
     * teamno :
     * oftencity : 0
     * oftendistinct : 0
     * oftensoccerpernum : 9
     * joinconfig : 0
     * sumary : ssssssssssss
     * establishdate : 1445875200000
     * teamleadernm : C
     * teamleaderusrrnm : 13811111112
     * teamleaderqqid : 13811111112
     * teamlogo : /group1/M00/00/01/CvvvY1YvQLiAJLMtAAB4hM7uju091.jpeg
     * actcount : 0
     * teamscore : 0
     * stat : 0
     * flag : 0
     * createdate : 1445937336000
     * updatedate : 1445937336000
     * teamrule :
     */

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
    private int actcount;
    private int teamscore;
    private String stat;
    private String flag;
    private Date createdate;
    private Date updatedate;
    private String teamrule;
    private float teambalance;

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void setTeamno(String teamno) {
        this.teamno = teamno;
    }

    public void setOftencity(int oftencity) {
        this.oftencity = oftencity;
    }

    public void setOftendistinct(int oftendistinct) {
        this.oftendistinct = oftendistinct;
    }

    public void setOftensoccerpernum(String oftensoccerpernum) {
        this.oftensoccerpernum = oftensoccerpernum;
    }

    public void setJoinconfig(int joinconfig) {
        this.joinconfig = joinconfig;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public void setEstablishdate(Date establishdate) {
        this.establishdate = establishdate;
    }

    public void setTeamleadernm(String teamleadernm) {
        this.teamleadernm = teamleadernm;
    }

    public void setTeamleaderusrrnm(String teamleaderusrrnm) {
        this.teamleaderusrrnm = teamleaderusrrnm;
    }

    public void setTeamleaderqqid(String teamleaderqqid) {
        this.teamleaderqqid = teamleaderqqid;
    }

    public void setTeamlogo(String teamlogo) {
        this.teamlogo = teamlogo;
    }

    public void setActcount(int actcount) {
        this.actcount = actcount;
    }

    public void setTeamscore(int teamscore) {
        this.teamscore = teamscore;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public void setTeamrule(String teamrule) {
        this.teamrule = teamrule;
    }

    public int getId() {
        return id;
    }

    public String getTeamname() {
        return teamname;
    }

    public String getTeamno() {
        return teamno;
    }

    public int getOftencity() {
        return oftencity;
    }

    public int getOftendistinct() {
        return oftendistinct;
    }

    public String getOftensoccerpernum() {
        return oftensoccerpernum;
    }

    public int getJoinconfig() {
        return joinconfig;
    }

    public String getSumary() {
        return sumary;
    }

    public Date getEstablishdate() {
        return establishdate;
    }

    public String getTeamleadernm() {
        return teamleadernm;
    }

    public String getTeamleaderusrrnm() {
        return teamleaderusrrnm;
    }

    public String getTeamleaderqqid() {
        return teamleaderqqid;
    }

    public String getTeamlogo() {
        return teamlogo;
    }

    public int getActcount() {
        return actcount;
    }

    public int getTeamscore() {
        return teamscore;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public String getTeamrule() {
        return teamrule;
    }

    public float getTeambalance() {
        return teambalance;
    }

    public void setTeambalance(float teambalance) {
        this.teambalance = teambalance;
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
}
