package com.metropolitan.cs330pz.util;

import android.content.Context;
import android.util.Log;

import com.metropolitan.cs330pz.R;
import com.metropolitan.cs330pz.entity.Recipe;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchAdapter {

    private Context context;
    private OnSearchResult listener;

    public interface OnSearchResult{

        void onResult(List<Recipe> recipeResults);
    }

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setOnSearchResultListener(OnSearchResult listener) {

        this.listener = listener;
    }

    List<Recipe> recipeList = Collections.synchronizedList( new LinkedList<Recipe>());


    public void findRecipesByTag(final String tag) {

        String url = context.getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);


        Call<List<Recipe>> call1 = jsonPlaceholderAPI.getRecipesByTag(tag);
        Call<List<Integer>> call2 = jsonPlaceholderAPI.getRecipeIDsByTag(tag);

        //Call for recipes
        call1.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> responseList = response.body();

                for(Recipe recipe : responseList) {

                    recipeList.add(recipe);
                    Log.e("Found recipe", recipe.toString());
                }

                if(listener != null) {

                    listener.onResult(recipeList);
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });

        //Call for RecipeTags
        call2.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                List<Integer> integerList = response.body();

                for(Integer integer : integerList) {

                    getRecipeById(integer);

                    Log.e("RecipeTag Fetch", String.valueOf(integer));

                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

                Log.e("Log", t.toString());
            }
        });



/*        //First thread
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {


            }
        });

        //Second thread
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {


            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


        //return recipeList;
    }

    public void getRecipeById(Integer id) {

        String url = context.getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);

        //Call for recipes by ID
        Call<Recipe> call3 = jsonPlaceholderAPI.getRecipeById(id);

        call3.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Recipe recipe = response.body();
                recipeList.add(recipe);
                Log.e("RecipeTag Recipe", recipe.toString());
                if(listener != null) {
                    listener.onResult(recipeList);
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });

        //return recipeList;
    }



}
