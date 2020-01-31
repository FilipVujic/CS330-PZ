package com.metropolitan.cs330pz.gui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;
import com.metropolitan.cs330pz.entity.Recipe;
import com.metropolitan.cs330pz.util.CustomAdapter;
import com.metropolitan.cs330pz.util.DBAdapter;
import com.metropolitan.cs330pz.util.JsonPlaceholderAPI;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SavedRecipesActivity extends AppCompatActivity {

    ArrayList<Recipe> dataModels;
    private static CustomAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        // put your code here...
        fetchData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.saved_recipes_layout);

        ListView listView = (ListView) findViewById(R.id.listViewID);

        dataModels = new ArrayList<>();

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recipe dataModel = dataModels.get(position);

                Intent goToRecipe = new Intent(getApplicationContext(), RecipeActivity.class);
                goToRecipe.putExtra("RecipeObj", dataModel);
                goToRecipe.putExtra("From activity", "saved_recipes");
                startActivity(goToRecipe);

                fetchData();
            }
        });


    }

    private void updateList(List<Recipe> recipes) {

        dataModels.clear();
        dataModels.addAll(recipes);
        adapter.notifyDataSetChanged();
    }

    private void fetchData() {

        DBAdapter db = new DBAdapter(this);
        Recipe recipe = null;
        db.open();
        Cursor c;
        c = db.getAllSavedRecipes();
        c.moveToFirst();
        List<Recipe> recipes = new LinkedList<>();

        Log.e("DB Operations", "Broj recepata " + c.getCount());

        if (c.getCount() > 0) {

            do {

                recipe = new Recipe(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8),
                        c.getString(9)
                );



                //Log.e("DB Operations", "Kolona 8 " + c.getString(8));

                //Log.e("DB Operations", "User logged in" + MainActivity.sharedPreferences.getString("user_logged-in", ""));

                if(recipe.saved_by.equals(MainActivity.sharedPreferences.getString("username", ""))) {
                    recipes.add(recipe);
                }

            } while (c.moveToNext());
        }

        updateList(recipes);
        db.close();
    }
}
