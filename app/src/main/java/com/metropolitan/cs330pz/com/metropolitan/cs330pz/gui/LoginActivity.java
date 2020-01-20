package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;
import com.metropolitan.cs330pz.entity.User;
import com.metropolitan.cs330pz.util.JsonPlaceholderAPI;

import org.apache.commons.codec.digest.DigestUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(MainActivity.sharedPreferences.getBoolean("user_logged-in", false)) {

            Log.e("Login", String.valueOf("User logged in: " + MainActivity.sharedPreferences.getBoolean("user_logged-in", false)));

            Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(goToHome);
            finish();
        }

        final EditText usernameField = (EditText)findViewById(R.id.login_username);
        final EditText passwdField = (EditText)findViewById(R.id.login_passwd);

        Button btnLogin = (Button)findViewById(R.id.login_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameField.getText().toString();
                String passwd = encryptPasswd(username, passwdField.getText().toString());

                checkCredentials(username, passwd);

            }
        });

    }

    public void goToReg(View view) {

        Intent goToRegister = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(goToRegister);
        finish();
    }


    public void checkCredentials(final String username, final String passwd) {

        String url = getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        Call<User> call = jsonPlaceholderAPI.getUser(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.e("Login", response.body() + "BODY");

                User user = response.body();

                if(user.password.equals(passwd)) {

                    logInUser(user.getUsername(), user.getEmail(), user.getPassword());

                    Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(goToHome);
                    finish();

                    /*Log.e("Login ",
                            "User \"" + username + "\" logged in with password \"" + user.password +"\"");

                    *//*MainActivity.sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
                    MainActivity.prefsEditor = MainActivity.sharedPreferences.edit();*//*

                    Log.e("NOTIFICATION", String.valueOf(MainActivity.sharedPreferences.getBoolean("user_logged-in", false)));

                    MainActivity.prefsEditor.putBoolean("user_logged-in", true);
                    MainActivity.prefsEditor.putString("username", user.getUsername());
                    MainActivity.prefsEditor.putString("email", user.getEmail());
                    MainActivity.prefsEditor.putString("passwd", user.getPassword());
                    MainActivity.prefsEditor.commit();

                    Log.e("NOTIFICATION", String.valueOf(MainActivity.sharedPreferences.getBoolean("user_logged-in", false)));

                    Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(goToHome);
                    finish();*/
                }
                else {Log.e("Login ERROR", "Wrong username or password!");}
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }



    public String encryptPasswd(String username, String passwd) {

        String passwdEncrypted;

        byte[] pwBytes = DigestUtils.sha1(username + passwd);
        passwdEncrypted = Base64.encodeToString(pwBytes, Base64.NO_WRAP);

        return passwdEncrypted;
    }

    public static void logInUser(String username, String email, String password) {

        Log.e("Login ",
                "User \"" + username + "\" logged in with password \"" + password +"\"");

                    /*MainActivity.sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
                    MainActivity.prefsEditor = MainActivity.sharedPreferences.edit();*/

        Log.e("NOTIFICATION", String.valueOf(MainActivity.sharedPreferences.getBoolean("user_logged-in", false)));

        MainActivity.prefsEditor.putBoolean("user_logged-in", true);
        MainActivity.prefsEditor.putString("username", username);
        MainActivity.prefsEditor.putString("email", email);
        MainActivity.prefsEditor.putString("passwd", password);
        MainActivity.prefsEditor.commit();

        Log.e("Login", String.valueOf(MainActivity.sharedPreferences.getBoolean("user_logged-in", false)));


    }

}
