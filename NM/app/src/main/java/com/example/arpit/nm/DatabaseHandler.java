package com.example.arpit.nm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 26/11/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "placeManager";

    // Places table name
    private static final String TABLE_CONTACTS = "places";
    private static final String KEY_ID="id";
    private static final String KEY_LAT="lat";
    private static final String KEY_LONGL="longl";
    private static final String KEY_NAME="name";
    private static final String KEY_RATE="rate";
    private static final String KEY_TOTAL="total";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LAT + " REAL,"
                + KEY_LONGL + " REAL" + KEY_RATE +"REAL"+ KEY_NAME +"TEXT"+ KEY_TOTAL +"INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }
    public void addPlaces(Places place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
         // Contact Name
        values.put(KEY_ID,place.getId());
        values.put(KEY_LAT, place.getLat()); // Contact Latitude
        values.put(KEY_LONGL, place.getLongl());

        values.put(KEY_RATE, place.getRate());
        values.put(KEY_NAME, place.getName());
        values.put(KEY_TOTAL, place.getTotal());


        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    // Getting single contact
    public Places getPlaces(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_LAT, KEY_LONGL,KEY_RATE,KEY_NAME,KEY_TOTAL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Places place = new Places(Integer.parseInt(cursor.getString(0)),
                Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)),Double.parseDouble(cursor.getString(3)), cursor.getString(4),Integer.parseInt(cursor.getString(5)));

        return place;
    }
    public List<Places> getAllPlaces() {
        List<Places> placelist = new ArrayList<Places>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Places place = new Places();
                place.setId(Integer.parseInt(cursor.getString(0)));
                place.setLat(Double.parseDouble(cursor.getString(1)));
                place.setLongl(Double.parseDouble(cursor.getString(2)));
                place.setRate(Double.parseDouble(cursor.getString(3)));
                place.setName(cursor.getString(4));
                place.setTotal(Integer.parseInt(cursor.getString(5)));

                // Adding contact to list
                placelist.add(place);
            } while (cursor.moveToNext());
        }

        // return contact list
        return placelist;
    }
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateContact(Places place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LAT, place.getLat()); // Contact Latitude
        values.put(KEY_LONGL, place.getLongl());

        values.put(KEY_RATE, place.getRate());
        values.put(KEY_NAME, place.getName());
        values.put(KEY_TOTAL, place.getTotal());



        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(place.getId())});
    }
    public void deleteContact(Places place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(place.getId())});
        db.close();
    }

}
