package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameField = (EditText)findViewById(R.id.login_username);
        final EditText passwdField = (EditText)findViewById(R.id.login_passwd);

        Button btnLogin = (Button)findViewById(R.id.login_btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameField.getText().toString();
                String passwd = encryptPasswd(username, passwdField.getText().toString());
                String storedUsername;
                String storedPasswd;

                checkCredentials(username, passwd);

            }
        });
    }

    public void goToReg(View view) {

        Intent goToRegister = new Intent(getApplicationContext(), Register.class);
        startActivity(goToRegister);
    }


    public void checkCredentials(final String username, final String passwd) {

        String url = MainActivity.url.toString();
                //+ "/webresources/entity.user"
                //+ "/" + username;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        Call<User> call = jsonPlaceholderAPI.getUser(username);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.e("Login error", response.body() + "BODY");

                User user = response.body();

                if(user.password.equals(passwd)) {

                    Log.e("Login ",
                            "User \"" + username + "\" logged in with password \"" + user.password +"\"");
                    Intent goToHome = new Intent(getApplicationContext(), Home.class);
                    startActivity(goToHome);
                    finish();
                }
                else {Log.e("Login error", "Wrong username or password!");}
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        /*RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String storedPasswd = response.getString("password");

                            *//*Log.e("Json passwd", storedPasswd);
                            Log.e("Encrypted passwd", passwd);*//*

                            if(storedPasswd.equals(passwd)) {
                                Log.e("Login ",
                                        "User \"" + username + "\" logged in with password \"" + storedPasswd +"\"");
                                Intent goToHome = new Intent(getApplicationContext(), Home.class);
                                startActivity(goToHome);
                            }
                            else {Log.e("Login error", "Wrong username or password!");}



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Volley error", error.toString());
                    }
                }

        );

        requestQueue.add(objectRequest);*/


    }



    public String encryptPasswd(String username, String passwd) {

        String passwdEncrypted = null;

        byte[] pwBytes = DigestUtils.sha1(username + passwd);
        passwdEncrypted = Base64.encodeToString(pwBytes, Base64.NO_WRAP);

        return passwdEncrypted;
    }



}
