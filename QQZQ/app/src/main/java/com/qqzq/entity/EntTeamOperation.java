package com.qqzq.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by jie.xiao on 15/10/14.
 */
public class EntTeamOperation {


    public final static int TEAM_MEMBER = 1;
    public final static int TEAM_GAME_LIST = 2;
    public final static int TEAM_CASH_PAY_RECORD = 3;
    public final static int TEAM_MEMBER_FEE_MANAGE = 4;
    public final static int TEAM_ATTENDANCE_MANAGE = 5;

    private int id;
    private Drawable logo;
    private String operation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getLogo() {
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
