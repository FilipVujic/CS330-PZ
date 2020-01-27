package com.metropolitan.cs330pz.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class HomeActivity extends AppCompatActivity {

    ArrayList<Recipe> dataModels;
    private static CustomAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final EditText searchField = (EditText) findViewById(R.id.home_search);
        final TextView resultField = (TextView) findViewById(R.id.home_result);
        final ListView listView = (ListView) findViewById(R.id.listViewID);

        dataModels = new ArrayList<>();

        adapter = new CustomAdapter(dataModels, getBaseContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Recipe dataModel = dataModels.get(position);

                Intent goToRecipe = new Intent(getBaseContext(), RecipeActivity.class);
                goToRecipe.putExtra("RecipeObj", dataModel);
                goToRecipe.putExtra("From activity", "home");
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

        Call<List<Recipe>> call = jsonPlaceholderAPI.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                List<Recipe> recipes = response.body();

                updateList(recipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

                showNoInternetAlert();
            }
        });
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
        MenuItem mnu1 = menu.add(0, 0, 0, "Profile");
        {

/*            mnu1.setAlphabeticShortcut('a');
            mnu1.setIcon(R.mipmap.ic_launcher_round);*/
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Saved Recipes");
        {
/*            mnu2.setAlphabeticShortcut('b');
            mnu2.setIcon(R.mipmap.ic_launcher_round);*/
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "My recipes");
        {
/*            mnu3.setAlphabeticShortcut('c');
            mnu3.setIcon(R.mipmap.ic_launcher_round);*/
        }


    }


    private boolean MenuChoice(MenuItem item) {
        switch (item.getItemId()) {

            case 0:
                Intent goToProfile = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(goToProfile);
                return true;

            case 1:
                Intent goToSavedRecipes = new Intent(getBaseContext(), SavedRecipesActivity.class);
                startActivity(goToSavedRecipes);
                return true;

            case 2:
                Intent goToMyRecipes = new Intent(getBaseContext(), MyRecipesActivity.class);
                startActivity(goToMyRecipes);
                return true;
        }
        return false;
    }


    public void goToCreateRecipe(View view) {

        Intent goToCreateRecipe = new Intent(getBaseContext(), CreateRecipeActivity.class);
        startActivity(goToCreateRecipe);
    }


    public void showNoInternetAlert() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
        builder1.setMessage("Can't connect to the internet. You can still browse your saved recipes.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = builder1.create();
        alertDialog.show();
    }

}
