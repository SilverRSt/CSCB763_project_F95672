package com.example.projetcparts_header_navigation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class databaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "android_app";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "clothes";

    private SQLiteDatabase database;

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME
                + " (clothes_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "clothe_type TEXT NOT NULL, "
                + "brand TEXT NOT NULL, "
                + "quantity INTEGER NOT NULL);";
        database.execSQL(sql);

        String populate = "INSERT INTO " + TABLE_NAME + " (clothe_type, brand, quantity) "
                + "VALUES (test1, brand1, 45);";
        database.execSQL(populate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
