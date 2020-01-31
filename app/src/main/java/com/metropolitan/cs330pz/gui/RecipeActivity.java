package com.metropolitan.cs330pz.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;
import com.metropolitan.cs330pz.entity.Recipe;
import com.metropolitan.cs330pz.util.DBAdapter;
import com.metropolitan.cs330pz.util.JsonPlaceholderAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeActivity extends AppCompatActivity {

    DBAdapter db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Recipe recipe;

        Intent test = getIntent();
        recipe = (Recipe) test.getSerializableExtra("RecipeObj");
        Log.e("NOTIFICATION", recipe.getRecipeTitle());


        View viewRecipe;
        LayoutInflater scrollLayout = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewRecipe = scrollLayout.inflate(R.layout.layout_recipe, null);

        ImageView image = viewRecipe.findViewById(R.id.recipe_layout_coverPhoto);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(getBaseContext()).load(recipe.getImage_url()).apply(options).into(image);

        TextView title = viewRecipe.findViewById(R.id.recipe_layout_title);
        title.setText(recipe.getRecipeTitle());

        TextView username = viewRecipe.findViewById(R.id.recipe_layout_username);
        username.setText("by " + recipe.getUsername());

        TextView synopsis = viewRecipe.findViewById(R.id.recipe_layout_synopsis);
        synopsis.setText(recipe.getSynopsis());

        TextView description = viewRecipe.findViewById(R.id.recipe_layout_description);
        description.setText(recipe.getDescription());

        TextView ingredients = viewRecipe.findViewById(R.id.recipe_layout_ingredients);
        ingredients.setText(recipe.getIngredients());

        TextView preparation = viewRecipe.findViewById(R.id.recipe_layout_preparation);
        preparation.setText(recipe.getPreparation());


        if (test.getStringExtra("From activity").equals("saved_recipes")) {

            Button btnRemoveFromFav = viewRecipe.findViewById(R.id.recipe_layout_btn);
            btnRemoveFromFav.setText("Remove from favourites");
            btnRemoveFromFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db = new DBAdapter(getBaseContext());
                    db.open();
                    boolean check = db.deleteRecipe(recipe.getId());
                    if (check) Toast.makeText(getBaseContext(), "Recipe removed from favourites.",
                            Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getBaseContext(), "Error! Operation failed.",
                            Toast.LENGTH_SHORT).show();
                    db.close();
                }
            });
        } else if (test.getStringExtra("From activity").equals("home")) {

            Button btnSaveToFav = viewRecipe.findViewById(R.id.recipe_layout_btn);

            if (isInLocalDB(recipe) || recipe.getUsername().equals(MainActivity.sharedPreferences.getString("username", ""))) {
                btnSaveToFav.setVisibility(View.GONE);
            } else {
                btnSaveToFav.setText("Save to favourites");
                btnSaveToFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db = new DBAdapter(getBaseContext());
                        db.open();
                        long check = db.insertRecipe(recipe);
                        if (check != -1)
                            Toast.makeText(getBaseContext(), "Recipe saved to favourites.",
                                    Toast.LENGTH_SHORT).show();
                        else Toast.makeText(getBaseContext(), "Error! Operation failed.",
                                Toast.LENGTH_SHORT).show();
                        db.close();
                    }
                });
            }

        } else if (test.getStringExtra("From activity").equals("my_recipes")) {

            Button btnSaveRecipe = viewRecipe.findViewById(R.id.recipe_layout_btn);
            btnSaveRecipe.setText("Delete recipe");
            btnSaveRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RecipeActivity.this);
                    builder1.setMessage("This will delete your recipe from the server and the local database. Do you wish to proceed?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    deleteRecipeFromServer(recipe.getId());

                                    if (isInLocalDB(recipe)) {

                                        db = new DBAdapter(getBaseContext());

                                        db.open();
                                        boolean check = db.deleteRecipe(recipe.getId());

                                        if (check)
                                            Toast.makeText(getBaseContext(), "Recipe deleted.",
                                                    Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getBaseContext(), "Error! Operation failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        db.close();
                                        dialog.cancel();
                                    }


                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = builder1.create();
                    alertDialog.show();

                }
            });
        }

        setContentView(viewRecipe);
    }

    private void deleteRecipeFromServer(int id) {

        String url = getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
        Call<ResponseBody> call = jsonPlaceholderAPI.deleteRecipe(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private boolean isInLocalDB(Recipe recipe) {

        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c;
        c = db.getRecipe(recipe.getId());

        //Log.e("Iz baze", c.getString(8));
        if (c.getCount() == 1) {

            if (recipe.getSaved_by().equals(MainActivity.sharedPreferences.getString("username", ""))) {
                db.close();
                return true;
            }
        }
        db.close();
        return false;
    }

}
