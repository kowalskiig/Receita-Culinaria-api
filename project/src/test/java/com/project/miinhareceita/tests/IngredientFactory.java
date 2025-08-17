package com.project.miinhareceita.tests;

import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.ingredient.dto.InsertIngredientDTO;
import com.project.miinhareceita.ingredient.dto.UpdateIngredientDTO;
import com.project.miinhareceita.ingredient.projection.IngredientProjection;

public class IngredientFactory {
    public static Ingredients createIngredient() {
        return new Ingredients(1L, "Tomate");
    }

    public static Ingredients createIngredient(Long id, String ingredientName) {
        return new Ingredients(1L, "Tomate");
    }

    public static InsertIngredientDTO createIngredientDto() {
        return new InsertIngredientDTO("Tomate");
    }

    public static IngredientProjection createIngredientProjection() {
        return new IngredientProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Tomate";
            }
        };
    }

    public static UpdateIngredientDTO createUpdateIngredientDTO() {
        return new UpdateIngredientDTO("Frango");
    }
    public static UpdateIngredientDTO createUpdateIngredientDTOWithOutData() {
        return new UpdateIngredientDTO();
    }


}
