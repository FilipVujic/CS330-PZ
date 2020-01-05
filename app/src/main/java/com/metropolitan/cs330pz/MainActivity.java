package com.metropolitan.cs330pz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui.Login;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static URL url;

    static {
        try {
            url = new URL("http://192.168.56.1:8080/CS330_PZ_RestServices/webresources/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent setInitialView = new Intent(getApplicationContext(), Login.class);
        startActivity(setInitialView);

        String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };

        String[] infoArray = {
                "8 tentacled monster",
                "Delicious in rolls",
                "Great for jumpers",
                "Nice in a stew",
                "Great for shoes",
                "Scary."
        };



    }
}
