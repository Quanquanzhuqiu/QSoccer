package com.qqzq.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by jie.xiao on 15/9/4.
 */
public class EntTeamListItem {
    private Drawable logo;
    private String teamName;
    private String teamCaptain;
    private String teamMembers;
    private String teamEstablishDay;

    public Drawable getLogo() {
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamCaptain() {
        return teamCaptain;
    }

    public void setTeamCaptain(String teamCaptain) {
        this.teamCaptain = teamCaptain;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getTeamEstablishDay() {
        return teamEstablishDay;
    }

    public void setTeamEstablishDay(String teamEstablishDay) {
        this.teamEstablishDay = teamEstablishDay;
    }
}
