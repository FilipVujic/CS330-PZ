package com.metropolitan.cs330pz.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_layout);

        TextView username = (TextView)findViewById(R.id.profile_username);
        TextView email = (TextView)findViewById(R.id.profile_email);

        username.setText(MainActivity.sharedPreferences.getString("username", ""));
        email.setText(MainActivity.sharedPreferences.getString("email", ""));





    }

    public void logOut(View view) {

        MainActivity.prefsEditor.remove("user_logged-in");
        MainActivity.prefsEditor.remove("username");
        MainActivity.prefsEditor.remove("email");
        MainActivity.prefsEditor.remove("passwd");
        MainActivity.prefsEditor.commit();

        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        goToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        goToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        goToLogin.putExtra("EXIT", true);
        startActivity(goToLogin);

        Log.e("Login", "User logged out");

        finish();
    }
}
