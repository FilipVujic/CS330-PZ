package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.metropolitan.cs330pz.R;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Recipe recipe;

        Intent test = getIntent();
        recipe = (Recipe)test.getSerializableExtra("RecipeObj");
        Log.e("NOTIFICATION", recipe.getRecipeTitle());

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
}
