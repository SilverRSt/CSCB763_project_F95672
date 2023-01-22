package com.example.projetcparts_header_navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "android_app";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "clothes";

    private static final String COLUMN_ID = "clothe_id";

    private static final String COLUMN_CLOTHES = "clothe_type";

    private static final String COLUMN_BRAND = "brand";

    private static final String COLUMN_QUAN = "quantity";

    private long size;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CLOTHES + " TEXT NOT NULL, "
                + COLUMN_BRAND + " TEXT NOT NULL, "
                + COLUMN_QUAN + "quantity INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    //set up start up values -> comment out on view after
    public void populateTable() {
        this.emptyTable();
//        SQLiteDatabase database = this.getWritableDatabase();

        this.addItem("T-shirt", "Adidas", 14);
        this.addItem("Shoes", "Adidas", 32);
        this.addItem("Shoes", "Nike", 13);
        this.addItem("Sweatshirt", "Apple", 3);
        this.addItem("Skirt", "Apple", 44);
        this.addItem("Skirt", "FENWICK", 6);
        this.addItem("Dress", "VISME", 21);

        //database.close();
    }

    public List<Cloth> getAllClothes() {
        List<Cloth> cloths = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(cursor.moveToFirst()) {
            do{
                String type = cursor.getString(1);
                String brand = cursor.getString(2);
                int quantity = cursor.getInt(3);

                cloths.add(new Cloth(type, brand, quantity));
            } while(cursor.moveToNext());
        }

        cursor.close();
        return cloths;
    }

    public void addItem(String type, String brand, int quan) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_CLOTHES, type);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_QUAN, quan);

        database.insert(TABLE_NAME, null, values);
    }

    public void setSize() {
        SQLiteDatabase database = this.getWritableDatabase();
        this.size =  DatabaseUtils.queryNumEntries(database, TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long getSize() {
        return size;
    }

    private void emptyTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME);
    }
}
