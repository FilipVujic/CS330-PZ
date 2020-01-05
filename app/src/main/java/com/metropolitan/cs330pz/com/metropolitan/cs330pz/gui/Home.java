package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity {

    public static JSONArray jsonArray;
    public static String [] nameArray;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final EditText searchField = (EditText) findViewById(R.id.home_search);


        //Log.e("Report", getRecipesJSON().optString("title"));

        JSONArray jsonArray;

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                String result = null;
                try {
                    URL url = new URL(MainActivity.url + "/webresources/entity.recipe");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    result = inputStreamToString(in);


                    Home.jsonArray = new JSONArray(result);

                    JSONObject jsonObject = Home.jsonArray.getJSONObject(0);

                    Log.e("HttpReq", jsonObject.optString("title"));

                } catch (Exception e) {
                    e.printStackTrace();

                }

                return null;
            }
        };

        asyncTask.execute();

        /*String[] nameArrayParam = new String[0];

        for (int i = 0; i < Home.jsonArray.length(); i++) {

            try {
                nameArrayParam[i] = Home.jsonArray.getJSONObject(i).optString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/


        // Uncomment ispod ovog, do kraja onCreate metode


/*        try {
            Home.nameArray = new String[] {
                    Home.jsonArray.getJSONObject(0).optString("title"),
                    //"Octopus",
                    "Pig",
                    "Sheep",
                    "Rabbit",
                    "Snake",
                    "Spider"};

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] infoArray = new String[0];

        infoArray = new String[]{
                //jsonArray[0].optString("title"),
                "8 tentacled monster",
                "Delicious in rolls",
                "Great for jumpers",
                "Nice in a stew",
                "Great for shoes",
                "Scary."
        };

        Drawable[] imageArray = {ContextCompat.getDrawable(this, R.drawable.user_icon),
                ContextCompat.getDrawable(this, R.drawable.user_icon),
                ContextCompat.getDrawable(this, R.drawable.user_icon),
                ContextCompat.getDrawable(this, R.drawable.user_icon),
                ContextCompat.getDrawable(this, R.drawable.user_icon),
                ContextCompat.getDrawable(this, R.drawable.user_icon)
        };

        ListView listView;

        RecipeAdapter whatever = new RecipeAdapter(this, nameArray, infoArray, imageArray);

        listView = (ListView) findViewById(R.id.listViewID);
        listView.setAdapter(whatever);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Home.this, RecipeDetails.class);
                String message = nameArray[position];
                intent.putExtra("animal", message);
                startActivity(intent);
            }
        });*/

    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        CreateMenu(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        CreateMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return MenuChoice(item);
    }

    private void CreateMenu(Menu menu) {
        menu.setQwertyMode(true);
        MenuItem mnu1 = menu.add(0, 0, 0, "Item1");
        {

            mnu1.setAlphabeticShortcut('a');
            mnu1.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Item2");
        {
            mnu2.setAlphabeticShortcut('b');
            mnu2.setIcon(R.mipmap.ic_launcher);
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "Item3");
        {
            mnu3.setAlphabeticShortcut('c');
            mnu3.setIcon(R.mipmap.ic_launcher);
        }


    }


    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(getApplicationContext(), "It works!", Toast.LENGTH_LONG).show();
                return true;
            case 1:
                return true;
            case 2:
                return true;
        }
        return false;
    }


    private static String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }
}
