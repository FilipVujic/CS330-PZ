package com.metropolitan.cs330pz.com.metropolitan.cs330pz.gui;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface JsonPlaceholderAPI {

    @GET("entity.user/{username}")
    Call<User> getUser(@Path("username") String username);

}
