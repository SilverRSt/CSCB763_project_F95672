package com.example.projetcparts_header_navigation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projetcparts_header_navigation.entity.Cloth;

import java.util.ArrayList;
import java.util.List;

/**
 * Database helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "android_app.db";

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

    /**
     * Create the table for the database on first call.
     * @param db
     *          the database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CLOTHES + " TEXT NOT NULL, "
                + COLUMN_BRAND + " TEXT NOT NULL, "
                + COLUMN_QUAN + " INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    /**
     * Empty table and then set up default values for the database to test scroll.
     */
    public void populateTable() {
        this.addItem("T-shirt", "Adidas", 14);
        this.addItem("Shoes", "Adidas", 32);
        this.addItem("Shoes", "Nike", 13);
        this.addItem("Sweatshirt", "Apple", 3);
        this.addItem("Skirt", "Apple", 44);
        this.addItem("Skirt", "FENWICK", 6);
        this.addItem("Shoes", "Apple", 21);
        this.addItem("Shoes", "VISME", 60);
        this.addItem("Sweatshirt", "Adidas", 120);
        this.addItem("Sweatshirt", "Adidas", 45);
        this.addItem("Dress", "Nike", 23);
        this.addItem("Shoes", "Apple", 32);
        this.addItem("shirt", "Nike", 55);
        this.addItem("shirt", "Nike", 53);
        this.addItem("Dress", "Nike", 24);
        this.addItem("Dress", "Nike", 3);
    }

    /**
     * Get an array list with all items in the table clothes.
     * @return
     *      Array List with all clothes from the database.
     */
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

    /**
     * Add new item to the table
     * @param type
     *          type of item
     * @param brand
     *          brand of item
     * @param quan
     *          quantity of item
     */
    public void addItem(String type, String brand, int quan) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_CLOTHES, type);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_QUAN, quan);

        database.insert(TABLE_NAME, null, values);
    }

    /**
     * Get the size of the database and set in in variable size
     */
    public void setSize() {
        SQLiteDatabase database = this.getWritableDatabase();
        this.size =  DatabaseUtils.queryNumEntries(database, TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public long getSize() {
        return size;
    }

    /**
     * Empty table clothes
     */
    private void emptyTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + TABLE_NAME);
    }
}
