package com.metropolitan.cs330pz.gui;

import android.content.Intent;
import android.os.Bundle;
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
import com.metropolitan.cs330pz.util.JsonPlaceholderAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRecipesActivity extends AppCompatActivity {

    ArrayList<Recipe> dataModels;
    private static CustomAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_recipes);

        /*final EditText searchField = (EditText) findViewById(R.id.home_search);
        final TextView resultField = (TextView) findViewById(R.id.home_result);*/
        final ListView listView = (ListView) findViewById(R.id.listViewID);

        dataModels = new ArrayList<>();

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recipe dataModel = dataModels.get(position);

                Intent goToRecipe = new Intent(getApplicationContext(), RecipeActivity.class);
                goToRecipe.putExtra("RecipeObj", dataModel);
                goToRecipe.putExtra("From activity", "my_recipes");
                startActivity(goToRecipe);


            }
        });

        fetchData();

    }

    private void updateList(List<Recipe> recipes) {

        dataModels.clear();
        dataModels.addAll(recipes);
        adapter.notifyDataSetChanged();
    }

    private void fetchData() {

        String url = getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        Call<List<Recipe>> call = jsonPlaceholderAPI.getUsersRecipes(MainActivity.sharedPreferences.getString("username", ""));

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                List<Recipe> recipes = response.body();

                updateList(recipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}
