package com.qqzq.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jie.xiao on 15/11/4.
 */
public class EntMemberBalanceInfo implements Serializable {


    /**
     * id : 0
     * userid : 0
     * username : string
     * usernickname : string
     * teamid : 0
     * teamname : string
     * joinway : 0
     * isleader : true
     * jointime : 2015-11-04T12:21:07.969Z
     * personalscore : 0
     * attendancecount : 0
     * stat : string
     * flag : string
     * createdate : 2015-11-04T12:21:07.969Z
     * updatedate : 2015-11-04T12:21:07.969Z
     */

    private List<EntTeamMember> memberBalances;

    public EntMemberBalanceInfo() {
        memberBalances = new ArrayList<>();
    }

    public void setMemberBalances(List<EntTeamMember> memberBalances) {
        this.memberBalances = memberBalances;
    }

    public List<EntTeamMember> getMemberBalances() {
        return memberBalances;
    }

}
