package com.metropolitan.cs330pz.gui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.metropolitan.cs330pz.MainActivity;
import com.metropolitan.cs330pz.R;
import com.metropolitan.cs330pz.entity.Recipe;
import com.metropolitan.cs330pz.entity.RecipeTag;
import com.metropolitan.cs330pz.entity.Tag;
import com.metropolitan.cs330pz.util.DBAdapter;
import com.metropolitan.cs330pz.util.JsonPlaceholderAPI;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_recipe);

        final EditText title = (EditText)findViewById(R.id.create_recipe_title);
        final EditText synopsis = (EditText)findViewById(R.id.create_recipe_synopsis);
        final EditText tags = (EditText) findViewById(R.id.create_recipe_tags);
        final EditText description = (EditText)findViewById(R.id.create_recipe_description);
        final EditText ingredients = (EditText)findViewById(R.id.create_recipe_ingredients);
        final EditText preparation = (EditText)findViewById(R.id.create_recipe_preparation);
        final EditText imageUrl = (EditText)findViewById(R.id.create_recipe_image_url);
        Button createRecipeBtn = (Button)findViewById(R.id.create_recipe_button);

        createRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titleStr = title.getText().toString();
                String synopsisStr = synopsis.getText().toString();
                String descriptionStr = description.getText().toString();
                String ingredientsStr = ingredients.getText().toString();
                String preparationStr = preparation.getText().toString();
                String imageUrlStr = imageUrl.getText().toString();

                Recipe recipe = new Recipe(
                        MainActivity.sharedPreferences.getString("username", ""),
                        titleStr,
                        synopsisStr,
                        descriptionStr,
                        ingredientsStr,
                        preparationStr,
                        imageUrlStr
                );
                postRecipe(recipe);

                //List<RecipeTag> listRecipeTag = new LinkedList<>();

                String[] listOfTags = tags.getText().toString().split(",");
                for(String tagName : listOfTags) {

                    Tag tag = new Tag(tagName);
                    postTags(tag);

                    //RecipeTag recipeTag = new RecipeTag(recipe.getId(), tagName);
                    postRecipeTags(listOfTags, recipe);
                }



            }
        });

    }

    public void postRecipe(final Recipe recipe) {

        String url = getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        Call<Recipe> call = jsonPlaceholderAPI.createRecipe(recipe);

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {


                DBAdapter db = new DBAdapter(getBaseContext());
                db.open();
                db.insertRecipe(recipe);
                db.close();
                Toast.makeText(getBaseContext(), "Recipe successfuly created!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
    }

    public void postTags(Tag tag) {

        String url = getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        Call<Tag> call = jsonPlaceholderAPI.createTag(tag);

        call.enqueue(new Callback<Tag>() {
            @Override
            public void onResponse(Call<Tag> call, Response<Tag> response) {

            }

            @Override
            public void onFailure(Call<Tag> call, Throwable t) {

            }
        });
    }

    public void postRecipeTags(String[] listOfTags, Recipe recipe) {

        String url = getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        //Call<Recipe> call = jsonPlaceholderAPI.getRe
    }

}
