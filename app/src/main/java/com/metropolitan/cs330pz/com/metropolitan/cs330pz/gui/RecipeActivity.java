package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Context;
import android.content.Intent;
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

public class RecipeActivity extends AppCompatActivity {

    DBAdapter db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Recipe recipe;

        Intent test = getIntent();
        recipe = (Recipe)test.getSerializableExtra("RecipeObj");
        Log.e("NOTIFICATION", recipe.getRecipeTitle());

        // Insert recipe into database (testing)
        db = new DBAdapter(this);

        db.open();
        long check = db.insertRecipe(
                recipe.getId(),
                recipe.getUsername(),
                recipe.getImage_url(),
                recipe.getRecipeTitle(),
                recipe.getSynopsis(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getPreparation(),
                MainActivity.sharedPreferences.getString("username", ""
                )

        );



        if (check != -1) Toast.makeText(getBaseContext(), "Podaci su uneti.",
                Toast.LENGTH_SHORT).show();
        else Toast.makeText(getBaseContext(), "Greska! Podaci nisu uneti.",
                Toast.LENGTH_SHORT).show();
        db.close();
        //END


        View viewRecipe;
        LayoutInflater scrollLayout = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewRecipe = scrollLayout.inflate(R.layout.recipe_layout, null);

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



        setContentView(viewRecipe);
    }

    public void saveRecipe(View view) {

        Toast.makeText(getBaseContext(), "Recipe saved to local database.", Toast.LENGTH_SHORT).show();
    }
}
