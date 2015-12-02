package com.qqzq.game.dto;

import java.util.Date;

/**
 * Created by jie.xiao on 15/12/2.
 */
public class EntGamePublishDTO {

    /**
     * teamid : 0
     * acttitle : string
     * actaddress : string
     * actcity : 0
     * actdistrict : 0
     * actdatetime : 2015-11-13T01:44:02.367Z
     * acttype : 0
     * actpaytype : 0
     * cost : 0.0
     * soccerpersonnum : 0
     * personmaxlimit : 0
     * personminlimit : 0
     * isrelatelist : true
     * isdinner : true
     */

    private int teamid;
    private String acttitle;
    private String actaddress;
    private int actcity;
    private int actdistrict;
    private Date actdatetime;
    private int acttype;
    private int actpaytype;
    private double cost;
    private int soccerpersonnum;
    private int personmaxlimit;
    private int personminlimit;
    private boolean isrelatelist;
    private boolean isdinner;

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public void setActtitle(String acttitle) {
        this.acttitle = acttitle;
    }

    public void setActaddress(String actaddress) {
        this.actaddress = actaddress;
    }

    public void setActcity(int actcity) {
        this.actcity = actcity;
    }

    public void setActdistrict(int actdistrict) {
        this.actdistrict = actdistrict;
    }

    public void setActtype(int acttype) {
        this.acttype = acttype;
    }

    public void setActpaytype(int actpaytype) {
        this.actpaytype = actpaytype;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setSoccerpersonnum(int soccerpersonnum) {
        this.soccerpersonnum = soccerpersonnum;
    }

    public void setPersonmaxlimit(int personmaxlimit) {
        this.personmaxlimit = personmaxlimit;
    }

    public void setPersonminlimit(int personminlimit) {
        this.personminlimit = personminlimit;
    }

    public void setIsrelatelist(boolean isrelatelist) {
        this.isrelatelist = isrelatelist;
    }

    public void setIsdinner(boolean isdinner) {
        this.isdinner = isdinner;
    }

    public int getTeamid() {
        return teamid;
    }

    public String getActtitle() {
        return acttitle;
    }

    public String getActaddress() {
        return actaddress;
    }

    public int getActcity() {
        return actcity;
    }

    public int getActdistrict() {
        return actdistrict;
    }

    public int getActtype() {
        return acttype;
    }

    public int getActpaytype() {
        return actpaytype;
    }

    public double getCost() {
        return cost;
    }

    public int getSoccerpersonnum() {
        return soccerpersonnum;
    }

    public int getPersonmaxlimit() {
        return personmaxlimit;
    }

    public int getPersonminlimit() {
        return personminlimit;
    }

    public boolean isIsrelatelist() {
        return isrelatelist;
    }

    public boolean isIsdinner() {
        return isdinner;
    }

    public Date getActdatetime() {
        return actdatetime;
    }

    public void setActdatetime(Date actdatetime) {
        this.actdatetime = actdatetime;
    }
}
