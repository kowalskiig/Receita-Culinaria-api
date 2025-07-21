package com.project.miinhareceita.tests;

import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.ingredient.dto.InsertIngredientDTO;

public class IngredientFactory {
    public static Ingredients createIngredient(){
        return new Ingredients(1L, "Tomate");
    }
    public static Ingredients createIngredient(Long id, String ingredientName){
        return new Ingredients(1L, "Tomate");
    }
    public static InsertIngredientDTO createIngredientDto(){
        return new InsertIngredientDTO("Tomate");
    }

}
