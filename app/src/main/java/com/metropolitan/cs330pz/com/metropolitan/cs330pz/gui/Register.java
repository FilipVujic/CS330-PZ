package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Intent;

import android.os.Bundle;

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

                String username = usernameField.getText().toString();
                String email = emailField.getText().toString();
                String passwd = encryptPasswd(username, passwdField.getText().toString());

                try {
                    postUser(username, email, passwd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goToLogin(View view) {

        Intent goToLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(goToLogin);
    }

    public void postUser(final String username, final String email, final String passwd) throws IOException {

        //DON'T DELETE
/*                try {
                    JSONObject jsonUser = new JSONObject();

                    URL obj = new URL("http://192.168.0.12:8080/CS330_PZ_RestServices/webresources/entity.users/");


                    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
                    postConnection.setRequestMethod("POST");
                    //postConnection.setRequestProperty("userId", "a1bcdefgh");
                    postConnection.setRequestProperty("Accept", "application/json");
                    postConnection.setRequestProperty("Content-Type", "application/json");
                    postConnection.setDoOutput(true);

                    int responseCode = postConnection.getResponseCode();


                    jsonUser.put("username", username);
                    jsonUser.put("email", email);
                    jsonUser.put("password", passwd);

                    Toast.makeText(getApplicationContext(), jsonUser.toString(), Toast.LENGTH_LONG).show();


                    OutputStream os = postConnection.getOutputStream();
                    os.write(jsonUser.toString().getBytes());
                    Log.e("POST REPORT", jsonUser.toString());
                    os.flush();
                    os.close();

                    System.out.println("POST Response Code :  " + responseCode);
                    System.out.println("POST Response Message : " + postConnection.getResponseMessage());

                    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                postConnection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        // print result
                        System.out.println(response.toString());

                    } else {
                        System.out.println("POST NOT WORKED");
                    }
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


        try {



            JSONObject jsonUser = new JSONObject();
            jsonUser.put("username", username);
            jsonUser.put("email", email);
            jsonUser.put("password", passwd);

            URL url = new URL(MainActivity.url + "/webresources/entity.user/");


            JsonObjectRequest jsonUserReq = new JsonObjectRequest(Request.Method.POST, url.toString(), jsonUser,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onBackPressed();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    final Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password", passwd);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(jsonUserReq);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String encryptPasswd(String username, String passwd) {

        String passwdEncrypted = null;

        byte[] pwBytes = DigestUtils.sha1(username + passwd);
        passwdEncrypted = Base64.encodeToString(pwBytes, Base64.NO_WRAP);

        return passwdEncrypted;
    }
}
