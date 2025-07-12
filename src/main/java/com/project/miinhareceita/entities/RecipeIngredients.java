package com.project.miinhareceita.entities;

import com.project.miinhareceita.entities.entitiespk.RecipeIngredientsPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "tb_recipe_ingredients")
public class RecipeIngredients {

    @EmbeddedId
    private RecipeIngredientsPk id = new RecipeIngredientsPk();

    @Getter
    @Setter
    private Integer quantity;
    @Getter
    @Setter
    private Double price;

    public RecipeIngredients(){

    }

    public RecipeIngredients (Recipe recipe, Ingredients ingredients, Integer quantity, Double price){
        id.setRecipe(recipe);
        id.setIngredients(ingredients);
        this.quantity = quantity;
        this.price = price;
    }
    public Recipe getRecipe() {
        return id.getRecipe();
    }

    public void setRecipe(Recipe recipe) {
        id.setRecipe(recipe);
    }

    public Ingredients getIngredient() {
        return id.getIngredients();
    }

    public void setIngredients(Ingredients ingredients) {
        id.setIngredients(ingredients);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecipeIngredients recipeIngredients = (RecipeIngredients) o;

        return Objects.equals(id, recipeIngredients.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}
