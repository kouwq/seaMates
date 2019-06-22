package com.demo.hello.seamates;

public class TeamItem {
    private int teamId, matesNum;
    private String cpName, teamName, leader, qq, detail;

    public TeamItem() {
        super();
        this.cpName = "";
        this.teamName = "";
        this.leader = "";
        this.matesNum = 0;
        this.qq = "";
        this.detail = "";
    }

    public TeamItem(String cpName, String teamName, String leader, int matesNum, String qq, String detail) {
        super();
        this.cpName = cpName;
        this.teamName = teamName;
        this.leader = leader;
        this.matesNum = matesNum;
        this.qq = qq;
        this.detail = detail;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public int getMatesNum() {
        return matesNum;
    }

    public void setMatesNum(int matesNum) {
        this.matesNum = matesNum;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
