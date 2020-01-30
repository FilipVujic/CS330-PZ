package com.metropolitan.cs330pz.util;

import com.metropolitan.cs330pz.entity.Recipe;
import com.metropolitan.cs330pz.entity.RecipeTag;
import com.metropolitan.cs330pz.entity.Tag;
import com.metropolitan.cs330pz.entity.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("entity.recipe/{id}")
    Call<Recipe> getRecipeById(@Path("id") Integer id);

    @POST("entity.recipe/")
    Call<Recipe> createRecipe(@Body Recipe recipe);

    /*@POST("entity.recipe/createAndReturnId")
    Call<Recipe> createRecipeAndReturnId(@Body Recipe recipe);*/

    @GET("entity.recipe/queryObjList/{username}")
    Call<List<Recipe>> getUsersRecipes(@Path("username") String username);

    @GET("entity.recipe/queryTag/{tag}")
    Call<List<Recipe>> getRecipesByTag(@Path("tag") String tag);

    @GET("entity.recipe/queryObj/{username}/{date}")
    Call<Recipe> getUsersRecipesByDate(@Path("username") String username, @Path("date") String date);

    @GET("entity.recipe/queryID/{username}/{date}")
    Call<Integer> getUsersRecipeIdByDate(@Path("username") String username, @Path("date") String date);

    @DELETE("entity.recipe/{id}")
    Call<ResponseBody> deleteRecipe(@Path("id") int id);

    @POST("entity.tag/")
    Call<Tag> createTag(@Body Tag tag);

    @POST("entity.recipetag/")
    Call<RecipeTag> createRecipeTag(@Body RecipeTag recipeTag);

    @GET("entity.recipetag/queryTag/{tag}")
    Call<List<Integer>> getRecipeIDsByTag(@Path("tag") String tag);
}
