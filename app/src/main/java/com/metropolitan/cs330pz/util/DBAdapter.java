package com.metropolitan.cs330pz.util;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DBAdapter extends SQLiteOpenHelper {

    static final String KEY_ID = "id";
    static final String KEY_USERNAME = "username";
    static final String KEY_IMAGE_URL = "image_url";
    static final String KEY_TITLE = "title";
    static final String KEY_SYNOPSIS = "synopsis";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_INGREDIENTS = "ingredients";
    static final String KEY_PREPARATION = "preparation";
    static final String KEY_SAVED_BY = "saved_by";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "cs330pz.db";
    static final String DATABASE_TABLE = "saved_recipes";
    static final int DATABASE_VERSION = 1;


    SQLiteDatabase db;

    public DBAdapter(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + DATABASE_TABLE + " ("
                + "id integer primary key AUTOINCREMENT, "
                + "username text, "
                + "image_url text,"
                + "title text,"
                + "synopsis text,"
                + "description text,"
                + "ingredients text,"
                + "preparation text,"
                + "saved_by text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(TAG, "Ažuriranje verzije baze podataka sa " + oldVersion + " na verziju "
                + newVersion + ", a to će uništiti postojeće podatke");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }


    public boolean open() throws SQLException {
        db = this.getWritableDatabase();
        return db != null;
    }

    //---zatvaranje baze podataka---
    public void close() {
        db.close();
    }

    //---umetanje recepta u bazu---
    public long insertRecipe(int id, String username, String image_url, String title, String synopsis, String description, String ingredients, String preparation, String saved_by) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_IMAGE_URL, image_url);
        contentValues.put(KEY_TITLE, title);
        contentValues.put(KEY_SYNOPSIS, synopsis);
        contentValues.put(KEY_DESCRIPTION, description);
        contentValues.put(KEY_INGREDIENTS, ingredients);
        contentValues.put(KEY_PREPARATION, preparation);
        contentValues.put(KEY_SAVED_BY, saved_by);
        return db.insert(DATABASE_TABLE, null, contentValues);
    }

/*    //---brisanje konkretnog kontakta---
    public boolean deleteContact(long broj_indeksa) {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + broj_indeksa, null) > 0;
    }

    //---preuzima sve kontakte---
    public Cursor getAllContacts() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME,
                KEY_BROJ_BODOVA}, null, null, null, null, null);
    }

    //---preuzima konkretan kontakt---
    public Cursor getContact(long broj_indeksa) throws SQLException {
        Cursor c =
                db.query(true, DATABASE_TABLE, new String[]{KEY_ID,
                                KEY_NAME, KEY_BROJ_BODOVA}, KEY_ID + "=" + broj_indeksa, null,
                        null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean updateContact(int broj_indeksa, ContentValues values) {

        int check;

        check = db.update(DATABASE_TABLE, values, KEY_ID + "=" + broj_indeksa, null );

        if (check == -1) return false;
        return true;
    }*/

}
