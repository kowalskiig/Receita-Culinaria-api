package com.project.miinhareceita.tests;

import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import com.project.miinhareceita.recipe.dto.InsertRecipeDTO;
import com.project.miinhareceita.recipe.dto.RecipeIngredientsDTO;
import com.project.miinhareceita.recipe.projection.RecipeProjections;
import com.project.miinhareceita.user.domain.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RecipeFactory {
    public static Recipe createRecipe() {
        User user = UserFactory.createUser();
        return new Recipe(user, "imgUrl", Instant.now(), 5, "instructions", 10, "shortDescription", "title", 1L);
    }

    public static RecipeProjections createRecipeProjections() {
        return new RecipeProjections() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getTitle() {
                return "Onigiri";
            }

            @Override
            public Integer getTimeMinutes() {
                return 5;
            }

            @Override
            public Instant getPublicationDate() {
                return Instant.now();
            }

            @Override
            public String getUrlImg() {
                return "img_url";
            }
        };
    }

    public static InsertRecipeDTO createInsertRecipeDto(){

 return new InsertRecipeDTO(
                createRecipe()
        );
    }


}