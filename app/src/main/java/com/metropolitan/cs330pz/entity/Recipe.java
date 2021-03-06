package com.metropolitan.cs330pz.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recipe implements Serializable {


/*    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    @Expose
    public Integer id;
    public String username;
    @SerializedName("imageUrl")
    public String image_url;
    public String title;
    public String synopsis;
    public String description;
    public String ingredients;
    public String preparation;
    @SerializedName("dateInserted")
    public String date_inserted;
    public String saved_by;

    public Recipe(Integer id, String username, String title, String synopsis, String description, String ingredients, String preparation, String image_url, String saved_by, String date_inserted) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.synopsis = synopsis;
        this.description = description;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.image_url = image_url;
        this.saved_by = saved_by;
        this.date_inserted = date_inserted;
    }

    public Recipe(String username, String title, String synopsis, String description, String ingredients, String preparation, String image_url, String date_inserted) {
        this.username = username;
        this.title = title;
        this.synopsis = synopsis;
        this.description = description;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.image_url = image_url;
        this.date_inserted = date_inserted;
    }

    public Recipe(String username, String title, String synopsis, String description, String ingredients, String preparation, String image_url, String saved_by, String date_inserted) {
        this.username = username;
        this.title = title;
        this.synopsis = synopsis;
        this.description = description;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.image_url = image_url;
        this.saved_by = saved_by;
        this.date_inserted = date_inserted;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", image_url='" + image_url + '\'' +
                ", title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", description='" + description + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", preparation='" + preparation + '\'' +
                ", date_inserted='" + date_inserted + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRecipeTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }
    //
    public String getDate_inserted() {
        return date_inserted;
    }

    public void setDate_inserted(String date_inserted) {
        this.date_inserted = date_inserted;
    }

    public String getSaved_by() {
        return saved_by;
    }

    public void setSaved_by(String saved_by) {
        this.saved_by = saved_by;
    }
}
