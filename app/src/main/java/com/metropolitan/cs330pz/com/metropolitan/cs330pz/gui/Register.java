package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;

import android.util.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.net.MalformedURLException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameField = (EditText) findViewById(R.id.register_username);
        final EditText emailField = (EditText) findViewById(R.id.register_email);
        final EditText passwdField = (EditText) findViewById(R.id.register_passwd);

        Button btnRegister = (Button) findViewById(R.id.register_btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                User user = new User(
                        usernameField.getText().toString(),
                        emailField.getText().toString(),
                        encryptPasswd(usernameField.getText().toString(),
                                passwdField.getText().toString())
                );

                postUser(user);
            }
        });
    }

    public void goToLogin(View view) {

        Intent goToLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(goToLogin);
        finish();
    }

    public void postUser(User user) {

        String url = getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        Call<User> call = jsonPlaceholderAPI.createUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                Toast.makeText(getBaseContext(), "User successfuly created! Redirecting to login screen.", Toast.LENGTH_SHORT).show();

                Intent goToHome = new Intent(getApplicationContext(), Home.class);
                startActivity(goToHome);
                finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public String encryptPasswd(String username, String passwd) {

        String passwdEncrypted = null;

        byte[] pwBytes = DigestUtils.sha1(username + passwd);
        passwdEncrypted = Base64.encodeToString(pwBytes, Base64.NO_WRAP);

        return passwdEncrypted;
    }
}
