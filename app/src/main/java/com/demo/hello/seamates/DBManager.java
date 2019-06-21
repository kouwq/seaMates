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

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(CompetitionItem item) {
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

    public void addAll(List<CompetitionItem> list) {
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

    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, null, null);
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(CompetitionItem item) {
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

    public List<CompetitionItem> listAll() {
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

    public CompetitionItem findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
        CompetitionItem CompetitionItem = null;
        if (cursor != null && cursor.moveToFirst()) {
            CompetitionItem = new CompetitionItem();
            CompetitionItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            CompetitionItem.setCpName(cursor.getString(cursor.getColumnIndex("CPNAME")));
            CompetitionItem.setCategory(cursor.getString(cursor.getColumnIndex("CATEGORY")));
            CompetitionItem.setCpStime(cursor.getString(cursor.getColumnIndex("CPSTIME")));
            CompetitionItem.setCpEtime(cursor.getString(cursor.getColumnIndex("CPETIME")));
            CompetitionItem.setCpInfo(cursor.getString(cursor.getColumnIndex("CPINFO")));
            cursor.close();
        }
        db.close();
        return CompetitionItem;
    }
}
