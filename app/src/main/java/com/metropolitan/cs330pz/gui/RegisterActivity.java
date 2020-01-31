package com.metropolitan.cs330pz.gui;

import android.content.Intent;

import android.os.Bundle;

import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330pz.R;
import com.metropolitan.cs330pz.entity.User;
import com.metropolitan.cs330pz.util.AsteriskPasswordTransformationMethod;
import com.metropolitan.cs330pz.util.JsonPlaceholderAPI;

import android.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameField = (EditText) findViewById(R.id.register_username);
        final EditText emailField = (EditText) findViewById(R.id.register_email);
        final EditText passwdField = (EditText) findViewById(R.id.register_passwd);
        passwdField.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        final EditText repeatPasswdField = (EditText) findViewById(R.id.repeat_register_passwd);
        repeatPasswdField.setTransformationMethod(new AsteriskPasswordTransformationMethod());

        Button btnRegister = (Button) findViewById(R.id.register_btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(passwdField.getText().toString().equals(repeatPasswdField.getText().toString())) {

                    User user = new User(
                            usernameField.getText().toString(),
                            emailField.getText().toString(),
                            encryptPasswd(usernameField.getText().toString(),
                                    passwdField.getText().toString())
                    );

                    postUser(user);
                }
                else Toast.makeText(getBaseContext(), "Passwords don't match!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void goToLogin(View view) {

        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(goToLogin);
        finish();
    }

    public void postUser(final User user) {

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

                Toast.makeText(getBaseContext(), "User successfuly created! Redirecting to home screen.", Toast.LENGTH_SHORT).show();
                SystemClock.sleep(500);

                LoginActivity.logInUser(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword()
                );

                Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
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
