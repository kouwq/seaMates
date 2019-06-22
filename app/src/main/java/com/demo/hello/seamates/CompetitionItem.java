package com.demo.hello.seamates;

public class CompetitionItem {
    private int id;
    private String cpName, category, cpStime, cpEtime, cpInfo;

    public CompetitionItem() {
        super();
        this.cpName = "";
        this.category = "";
        this.cpStime = "";
        this.cpEtime = "";
        this.cpInfo = "";
    }

    public CompetitionItem(String cpName, String category, String cpStime, String cpEtime, String cpInfo) {
        super();
        this.cpName = cpName;
        this.category = category;
        this.cpStime = cpStime;
        this.cpEtime = cpEtime;
        this.cpInfo = cpInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCpStime() {
        return cpStime;
    }

    public void setCpStime(String cpStime) {
        this.cpStime = cpStime;
    }

    public String getCpEtime() {
        return cpEtime;
    }

    public void setCpEtime(String cpEtime) {
        this.cpEtime = cpEtime;
    }

    public String getCpInfo() {
        return cpInfo;
    }

    public void setCpInfo(String cpInfo) {
        this.cpInfo = cpInfo;
    }
}
