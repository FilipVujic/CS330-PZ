package com.metropolitan.cs330pz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.metropolitan.cs330pz.gui.LoginActivity;
import com.metropolitan.cs330pz.util.DBAdapter;

public class MainActivity extends AppCompatActivity {


    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        prefsEditor = MainActivity.sharedPreferences.edit();

        //empty database
        /*DBAdapter db = new DBAdapter(this);
        db.open();
        db.deleteRecipe(1);
        db.deleteRecipe(4);
        db.close();*/

        //end

        Intent setInitialView = new Intent(this, LoginActivity.class);
        startActivity(setInitialView);
        finish();




    }
}
