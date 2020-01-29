package com.metropolitan.cs330pz.entity;

import com.google.gson.annotations.SerializedName;

public class RecipeTag {

    public int id;
    @SerializedName("recipeID")
    public int recipeId;
    public String tagName;

    public RecipeTag(int id, int recipeId, String tagName) {
        this.id = id;
        this.recipeId = recipeId;
        this.tagName = tagName;
    }

    public RecipeTag(int recipeId, String tagName) {
        this.recipeId = recipeId;
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "RecipeTag{" +
                "id=" + id +
                ", recipeId=" + recipeId +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
