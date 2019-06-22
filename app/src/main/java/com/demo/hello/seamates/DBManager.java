package com.demo.hello.seamates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private DBHelper dbHelper;
    private String TBNAME;
    private String TBTEAM;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
        TBTEAM = DBHelper.TB_TEAM;
    }

    public void addCp(CompetitionItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cpname", item.getCpName());
        values.put("category", item.getCategory());
        values.put("cpstime", item.getCpStime());
        values.put("cpetime", item.getCpEtime());
        values.put("cpinfo", item.getCpInfo());
        db.insert(TBNAME, null, values);
        db.close();
    }

    public void addAllCp(List<CompetitionItem> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (CompetitionItem item : list) {
            ContentValues values = new ContentValues();
            values.put("cpname", item.getCpName());
            values.put("category", item.getCategory());
            values.put("cpstime", item.getCpStime());
            values.put("cpetime", item.getCpEtime());
            values.put("cpinfo", item.getCpInfo());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public void deleteAllCp() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, null, null);
        db.close();
    }

    public void deleteCp(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateCp(CompetitionItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cpname", item.getCpName());
        values.put("category", item.getCategory());
        values.put("cpstime", item.getCpStime());
        values.put("cpetime", item.getCpEtime());
        values.put("cpinfo", item.getCpInfo());
        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<CompetitionItem> listAllCp() {
        List<CompetitionItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if (cursor != null) {
            rateList = new ArrayList<CompetitionItem>();
            while (cursor.moveToNext()) {
                CompetitionItem item = new CompetitionItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCpName(cursor.getString(cursor.getColumnIndex("CPNAME")));
                item.setCategory(cursor.getString(cursor.getColumnIndex("CATEGORY")));
                item.setCpStime(cursor.getString(cursor.getColumnIndex("CPSTIME")));
                item.setCpEtime(cursor.getString(cursor.getColumnIndex("CPETIME")));
                item.setCpInfo(cursor.getString(cursor.getColumnIndex("CPINFO")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

    public CompetitionItem findCpById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        CompetitionItem competitionItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            competitionItem = new CompetitionItem();
            competitionItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            competitionItem.setCpName(cursor.getString(cursor.getColumnIndex("CPNAME")));
            competitionItem.setCategory(cursor.getString(cursor.getColumnIndex("CATEGORY")));
            competitionItem.setCpStime(cursor.getString(cursor.getColumnIndex("CPSTIME")));
            competitionItem.setCpEtime(cursor.getString(cursor.getColumnIndex("CPETIME")));
            competitionItem.setCpInfo(cursor.getString(cursor.getColumnIndex("CPINFO")));
            cursor.close();
        }
        db.close();
        return competitionItem;
    }

    public void addTeam(TeamItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cpname", item.getCpName());
        values.put("teamname", item.getTeamName());
        values.put("leader", item.getLeader());
        values.put("matesnum", item.getMatesNum());
        values.put("qq", item.getQq());
        values.put("detail", item.getDetail());
        db.insert(TBTEAM, null, values);
        db.close();
    }

    public void addAllTeam(List<TeamItem> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (TeamItem item : list) {
            ContentValues values = new ContentValues();
            values.put("cpname", item.getCpName());
            values.put("teamname", item.getTeamName());
            values.put("leader", item.getLeader());
            values.put("matesnum", item.getMatesNum());
            values.put("qq", item.getQq());
            values.put("detail", item.getDetail());
            db.insert(TBTEAM, null, values);
        }
        db.close();
    }

    public void deleteAllTeam() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBTEAM, null, null);
        db.close();
    }

    public void deleteTeam(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBTEAM, "TEAM_ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateTeam(TeamItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cpname", item.getCpName());
        values.put("teamname", item.getTeamName());
        values.put("leader", item.getLeader());
        values.put("matesnum", item.getMatesNum());
        values.put("qq", item.getQq());
        values.put("detail", item.getDetail());
        db.update(TBTEAM, values, "TEAM_ID=?", new String[]{String.valueOf(item.getTeamId())});
        db.close();
    }

    public List<TeamItem> listAllTeam() {
        List<TeamItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBTEAM, null, null, null, null, null, null);
        if (cursor != null) {
            rateList = new ArrayList<TeamItem>();
            while (cursor.moveToNext()) {
                TeamItem item = new TeamItem();
                item.setTeamId(cursor.getInt(cursor.getColumnIndex("TEAM_ID")));
                item.setCpName(cursor.getString(cursor.getColumnIndex("CPNAME")));
                item.setTeamName(cursor.getString(cursor.getColumnIndex("TEAMNAME")));
                item.setLeader(cursor.getString(cursor.getColumnIndex("LEADER")));
                item.setMatesNum(cursor.getInt(cursor.getColumnIndex("MATESNUM")));
                item.setQq(cursor.getString(cursor.getColumnIndex("QQ")));
                item.setDetail(cursor.getString(cursor.getColumnIndex("DETAIL")));
                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

    public TeamItem findTeamById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBTEAM, null, "TEAM_ID=?", new String[]{String.valueOf(id)}, null, null, null);
        TeamItem teamItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            teamItem = new TeamItem();
            teamItem.setTeamId(cursor.getInt(cursor.getColumnIndex("TEAM_ID")));
            teamItem.setCpName(cursor.getString(cursor.getColumnIndex("CPNAME")));
            teamItem.setTeamName(cursor.getString(cursor.getColumnIndex("TEAMNAME")));
            teamItem.setLeader(cursor.getString(cursor.getColumnIndex("LEADER")));
            teamItem.setMatesNum(cursor.getInt(cursor.getColumnIndex("MATESNUM")));
            teamItem.setQq(cursor.getString(cursor.getColumnIndex("QQ")));
            teamItem.setDetail(cursor.getString(cursor.getColumnIndex("DETAIL")));
            cursor.close();
        }
        db.close();
        return teamItem;
    }
}
