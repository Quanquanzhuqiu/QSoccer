package com.qqzq.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jie.xiao on 15/11/4.
 */
public class CreateMemberBalanceRequest implements Serializable {


    private float totalReduction;
    private List<QqzqUserTeamRel> memberBalances;

    public CreateMemberBalanceRequest() {
        memberBalances = new ArrayList<>();
    }

    public float getTotalReduction() {
        return totalReduction;
    }

    public void setTotalReduction(float totalReduction) {
        this.totalReduction = totalReduction;
    }

    public List<QqzqUserTeamRel> getMemberBalances() {
        return memberBalances;
    }

    public void setMemberBalances(List<QqzqUserTeamRel> memberBalances) {
        this.memberBalances = memberBalances;
    }

    public class QqzqUserTeamRel implements Serializable{

        private int id;
        private int userid;
        private String username;
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
}
