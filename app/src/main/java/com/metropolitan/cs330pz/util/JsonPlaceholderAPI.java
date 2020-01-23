package com.metropolitan.cs330pz.util;

import com.metropolitan.cs330pz.entity.Recipe;
import com.metropolitan.cs330pz.entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface JsonPlaceholderAPI {

    @GET("entity.user/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("entity.user/")
    Call<User> createUser(@Body User user);

    @GET("entity.recipe/")
    Call<List<Recipe>> getRecipes();

    @POST("entity.recipe/")
    Call<Recipe> createRecipe(@Body Recipe recipe);

    @GET("entity.recipe/query/{username}")
    Call<List<Recipe>> getUsersRecipes(@Path("username") String username);


}
