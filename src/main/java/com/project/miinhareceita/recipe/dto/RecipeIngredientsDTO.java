package com.project.miinhareceita.recipe.dto;

import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RecipeIngredientsDTO {
    private Long recipeId;
    private Long ingredientId;
    private Integer quantity;
    private Double price;


    public RecipeIngredientsDTO(RecipeIngredients recipeIngredients){
        recipeId = recipeIngredients.getRecipe().getId();
        ingredientId = recipeIngredients.getIngredient().getId();
        quantity = recipeIngredients.getQuantity();
        price=  recipeIngredients.getPrice();
    }


}
