package com.qqzq.entity;

import java.util.Date;

/**
 * Created by jie.xiao on 15/9/16.
 */

/*
QqzqActivity {
    id (integer, optional),
    acttitle (string, optional),
    actaddress (string, optional),
    actcity (integer, optional),
    actdistrict (integer, optional),
    actdatetime (string, optional),
    actdate (string, optional),
    acttime (string, optional),
    personmaxlimit (integer, optional),
    personminlimit (integer, optional),
    acttype (integer, optional),
    actpaytype (integer, optional),
    cost (undefined, optional),
    isrelatelist (boolean, optional),
    soccerpersonnum (integer, optional),
    isdinner (boolean, optional),
    publisher (string, optional),
    publishdate (string, optional),
    stat (string, optional),
    flag (string, optional),
    createdate (string, optional),
    updatedate (string, optional),
    teamid (integer, optional)
}
*/
public class EntGameInfo {
    private int id;
    private String acttitle;
    private String actaddress;
    private int actcity;
    private int actdistrict;
    private Date actdatetime;
    private Date actdate;
    private Date acttime;
    private int personmaxlimit;
    private int personminlimit;
    private int acttype;
    private int actpaytype;
    private boolean isrelatelist;
    private int soccerpersonnum;
    private boolean isdinner;
    private String publisher;
    private Date publishdate;
    private String stat;
    private String flag;
    private Date createdate;
    private Date updatedate;
    private int teamid;
    private int cost;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActtitle() {
        return acttitle;
    }

    public void setActtitle(String acttitle) {
        this.acttitle = acttitle;
    }

    public String getActaddress() {
        return actaddress;
    }

    public void setActaddress(String actaddress) {
        this.actaddress = actaddress;
    }

    public int getActcity() {
        return actcity;
    }

    public void setActcity(int actcity) {
        this.actcity = actcity;
    }

    public int getActdistrict() {
        return actdistrict;
    }

    public void setActdistrict(int actdistrict) {
        this.actdistrict = actdistrict;
    }

    public Date getActdatetime() {
        return actdatetime;
    }

    public void setActdatetime(Date actdatetime) {
        this.actdatetime = actdatetime;
    }

    public Date getActdate() {
        return actdate;
    }

    public void setActdate(Date actdate) {
        this.actdate = actdate;
    }

    public Date getActtime() {
        return acttime;
    }

    public void setActtime(Date acttime) {
        this.acttime = acttime;
    }

    public int getPersonmaxlimit() {
        return personmaxlimit;
    }

    public void setPersonmaxlimit(int personmaxlimit) {
        this.personmaxlimit = personmaxlimit;
    }

    public int getPersonminlimit() {
        return personminlimit;
    }

    public void setPersonminlimit(int personminlimit) {
        this.personminlimit = personminlimit;
    }

    public int getActtype() {
        return acttype;
    }

    public void setActtype(int acttype) {
        this.acttype = acttype;
    }

    public int getActpaytype() {
        return actpaytype;
    }

    public void setActpaytype(int actpaytype) {
        this.actpaytype = actpaytype;
    }

    public boolean isrelatelist() {
        return isrelatelist;
    }

    public void setIsrelatelist(boolean isrelatelist) {
        this.isrelatelist = isrelatelist;
    }

    public int getSoccerpersonnum() {
        return soccerpersonnum;
    }

    public void setSoccerpersonnum(int soccerpersonnum) {
        this.soccerpersonnum = soccerpersonnum;
    }

    public boolean isdinner() {
        return isdinner;
    }

    public void setIsdinner(boolean isdinner) {
        this.isdinner = isdinner;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
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

    public int getTeamid() {
        return teamid;
    }

    public void setTeamid(int teamid) {
        this.teamid = teamid;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
