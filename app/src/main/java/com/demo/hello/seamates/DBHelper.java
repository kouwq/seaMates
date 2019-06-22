package com.demo.hello.seamates;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "android.db";
    public static final String TB_NAME = "tb_competitions";
    public static final String TB_TEAM = "tb_teams";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TB_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CPNAME TEXT,CATEGORY TEXT,CPSTIME TEXT,CPETIME TEXT,CPINFO TEXT)");
        db.execSQL("CREATE TABLE " + TB_TEAM + "(TEAM_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CPNAME TEXT,TEAMNAME TEXT,LEADER TEXT,MATESNUM TEXT,QQ TEXT,DETAIL TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
