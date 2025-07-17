package com.project.miinhareceita.dtos;

import com.project.miinhareceita.recipe.domain.RecipeIngredients;

public class RecipeIngredientsDTO {
    private Long recipeId;
    private Long ingredientId;
    private Integer quantity;
    private Double price;

    public RecipeIngredientsDTO(){
    }
    public RecipeIngredientsDTO(RecipeIngredients recipeIngredients){
        recipeId = recipeIngredients.getRecipe().getId();
        ingredientId = recipeIngredients.getIngredient().getId();
        quantity = recipeIngredients.getQuantity();
        price=  recipeIngredients.getPrice();
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Long getIngredientId() {
        return ingredientId;
    }
}
