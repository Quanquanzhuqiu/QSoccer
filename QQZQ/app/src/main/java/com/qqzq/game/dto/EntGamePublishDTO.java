package com.qqzq.game.dto;

import java.util.Date;

/**
 * Created by jie.xiao on 15/12/2.
 */
public class EntGamePublishDTO {

    /**
     * {
     "id": 0,
     "teamid": 0,
     "teamname": "string",
     "acttitle": "string",
     "actaddress": "string",
     "actcity": 0,
     "actdistrict": 0,
     "actdatetime": "2016-01-19T02:09:20.979Z",
     "actdate": "2016-01-19T02:09:20.980Z",
     "acttime": "2016-01-19T02:09:20.980Z",
     "personmaxlimit": 0,
     "personminlimit": 0,
     "acttype": 0,
     "actpaytype": 0,
     "isrelatelist": true,
     "soccerpersonnum": 0,
     "isdinner": true,
     "publisher": "string",
     "publishdate": "2016-01-19T02:09:20.980Z",
     "isend": true,
     "signupcount": 0,
     "attendancecount": 0,
     "belatecount": 0,
     "stat": "string",
     "flag": "string",
     "createdate": "2016-01-19T02:09:20.980Z",
     "updatedate": "2016-01-19T02:09:20.980Z"
     }
     */

    private int teamid;
    private int id;

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

    public String getActdate() {
        return actdate;
    }

    public void setActdate(String actdate) {
        this.actdate = actdate;
    }

    public String getActtime() {
        return acttime;
    }

    public void setActtime(String acttime) {
        this.acttime = acttime;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public boolean isend() {
        return isend;
    }

    public void setIsend(boolean isend) {
        this.isend = isend;
    }

    public int getSignupcount() {
        return signupcount;
    }

    public void setSignupcount(int signupcount) {
        this.signupcount = signupcount;
    }

    public int getAttendancecount() {
        return attendancecount;
    }

    public void setAttendancecount(int attendancecount) {
        this.attendancecount = attendancecount;
    }

    public int getBelatecount() {
        return belatecount;
    }

    public void setBelatecount(int belatecount) {
        this.belatecount = belatecount;
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

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public boolean isrelatelist() {
        return isrelatelist;
    }

    public boolean isdinner() {
        return isdinner;
    }

    private String teamname;
    private String actdate;
    private String acttime;
    private String publisher;
    private String publishdate;
    private boolean isend;
    private int signupcount;
    private int attendancecount;
    private int belatecount;
    private String stat;
    private String flag;
    private String createdate;
    private String updatedate;

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
