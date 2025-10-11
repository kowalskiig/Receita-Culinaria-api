package com.project.miinhareceita.domains.recipe.dto;

import com.project.miinhareceita.domains.recipe.domain.RecipeIngredients;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
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
