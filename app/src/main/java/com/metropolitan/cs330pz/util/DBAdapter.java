package com.metropolitan.cs330pz.util;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.entity.Recipe;

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
    static final String KEY_DATE_INSERTED = "date_inserted";
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
                + "saved_by text,"
                + "date_inserted text"
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
    public long insertRecipe(Recipe recipe) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //
        //contentValues.put(KEY_ID, recipe.getId());
        contentValues.put(KEY_USERNAME, recipe.getUsername());
        contentValues.put(KEY_IMAGE_URL, recipe.getImage_url());
        contentValues.put(KEY_TITLE, recipe.getRecipeTitle());
        contentValues.put(KEY_SYNOPSIS, recipe.getSynopsis());
        contentValues.put(KEY_DESCRIPTION, recipe.getDescription());
        contentValues.put(KEY_INGREDIENTS, recipe.getIngredients());
        contentValues.put(KEY_PREPARATION, recipe.getPreparation());
        contentValues.put(KEY_SAVED_BY, MainActivity.sharedPreferences.getString("username", ""));
        contentValues.put(KEY_DATE_INSERTED, recipe.getDate_inserted());
        return db.insert(DATABASE_TABLE, null, contentValues);
    }

    //---brisanje konkretnog kontakta---
    public boolean deleteRecipe(int id) {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + id, null) > 0;
    }

    //---preuzima sve kontakte---
    public Cursor getAllSavedRecipes() {
        return db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_USERNAME, KEY_IMAGE_URL, KEY_TITLE, KEY_SYNOPSIS, KEY_DESCRIPTION,
                KEY_INGREDIENTS, KEY_PREPARATION, KEY_SAVED_BY, KEY_DATE_INSERTED}, null, null, null, null, null);
    }

    //---preuzima konkretan kontakt---
    public Cursor getRecipe(int id) throws SQLException {
        Cursor c =
                db.query(true, DATABASE_TABLE, new String[]{KEY_ID, KEY_USERNAME, KEY_IMAGE_URL, KEY_TITLE, KEY_SYNOPSIS, KEY_DESCRIPTION,
                                KEY_INGREDIENTS, KEY_PREPARATION, KEY_SAVED_BY, KEY_DATE_INSERTED}, KEY_ID + "=" + id, null,
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
    }

}
