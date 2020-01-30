package com.metropolitan.cs330pz.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.metropolitan.cs330pz.R;
import com.metropolitan.cs330pz.entity.Recipe;

import java.io.IOException;
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

    public interface OnSearchResult {

        void onResult(List<Recipe> recipeResults);
    }

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public void setOnSearchResultListener(OnSearchResult listener) {

        this.listener = listener;
    }

    List<Recipe> recipeList = Collections.synchronizedList(new LinkedList<Recipe>());


    public void findRecipesByTag(final String tag) {

        String url = context.getResources().getString(R.string.url);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);


        @SuppressLint("StaticFieldLeak")
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {

                Call<List<Recipe>> call1 = jsonPlaceholderAPI.getRecipesByTag(tag);
                Call<List<Integer>> call2 = jsonPlaceholderAPI.getRecipeIDsByTag(tag);


                try {
                    Response<List<Recipe>> response1 = call1.execute();

                    List<Recipe> list1 = response1.body();

                    for (Recipe recipe : list1) {

                        recipeList.add(recipe);
                    }

                    Log.e("AsyncTask", "Gotov 1. task");

                    Response<List<Integer>> response2 = call2.execute();

                    List<Integer> list2 = response2.body();

                    for (Integer integer : list2) {
                        Call<Recipe> call3 = jsonPlaceholderAPI.getRecipeById(integer);
                        Response<Recipe> response3 = call3.execute();
                        Recipe recipe = response3.body();
                        recipeList.add(recipe);

                    }

                    Log.e("AsyncTask", "Gotov 2. task");


                    /*Log.e("Response 1", response1.body().toString());
                    Log.e("Response 2", response2.body().toString());*/
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (listener != null)
                    listener.onResult(recipeList);
            }
        };
        asyncTask.execute("");


    }

}
