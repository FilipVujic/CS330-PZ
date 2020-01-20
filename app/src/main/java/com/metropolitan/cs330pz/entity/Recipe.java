package com.metropolitan.cs330pz.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recipe implements Serializable {


/*    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }*/

    public int id;
    public String username;
    @SerializedName("imageUrl")
    public String image_url;
    public String title;
    public String synopsis;
    public String description;
    public String ingredients;
    public String preparation;

    public Recipe(int id, String username, String image_url, String title, String synopsis, String description, String ingredients, String preparation) {
        this.id = id;
        this.username = username;
        this.image_url = image_url;
        this.title = title;
        this.synopsis = synopsis;
        this.description = description;
        this.ingredients = ingredients;
        this.preparation = preparation;
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
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
