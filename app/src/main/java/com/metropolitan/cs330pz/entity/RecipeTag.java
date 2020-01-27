package com.metropolitan.cs330pz.entity;

public class RecipeTag {

    public int id;
    public int recipeID;
    public String tagName;

    public RecipeTag(int id, int recipeID, String tagName) {
        this.id = id;
        this.recipeID = recipeID;
        this.tagName = tagName;
    }

    public RecipeTag(int recipeID, String tagName) {
        this.recipeID = recipeID;
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "RecipeTag{" +
                "id=" + id +
                ", recipeID=" + recipeID +
                ", tagName='" + tagName + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
