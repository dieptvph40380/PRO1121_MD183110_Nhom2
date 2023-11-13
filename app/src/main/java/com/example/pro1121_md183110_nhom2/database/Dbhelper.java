package com.example.pro1121_md183110_nhom2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper  extends SQLiteOpenHelper {

    public Dbhelper(Context context){
        super(context,"Nhom2",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qAdmin="CREATE TABLE ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
