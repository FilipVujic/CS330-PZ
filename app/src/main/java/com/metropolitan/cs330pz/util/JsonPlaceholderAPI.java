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
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface JsonPlaceholderAPI {

    @GET("entity.user/{username}")
    Call<User> getUser(@Path("username") String username);

    @POST("entity.user/")
    Call<User> createUser(@Body User user);

    @GET("entity.recipe/")
    Call<List<Recipe>> getRecipes();

    /*@GET("entity.recipe/query/{title_username}")
    Call<Recipe> getSpecificRecipe(@Path("title_username") String title_username);*/

    @POST("entity.recipe/")
    Call<Recipe> createRecipe(@Body Recipe recipe);

    @GET("entity.recipe/query/{username}")
    Call<List<Recipe>> getUsersRecipes(@Path("username") String username);

    @DELETE("entity.recipe/{id}")
    Call<ResponseBody> deleteRecipe(@Path("id") int id);

    @POST("entity.tag/")
    Call<Tag> createTag(@Body Tag tag);

    @GET("entity.tag/")
    Call<List<Tag>> getAllTags();

    @GET("entity.tag/{name}")
    Call<Tag> getTag(@Path("name") String name);

    @POST("entity.recipetag/")
    Call<RecipeTag> createRecipeTag(@Body RecipeTag recipeTag);


}
