package com.metropolitan.cs330pz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui.LoginActivity;

public class MainActivity extends AppCompatActivity {


    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        prefsEditor = MainActivity.sharedPreferences.edit();

        Intent setInitialView = new Intent(this, LoginActivity.class);
        startActivity(setInitialView);
        finish();




    }
}
