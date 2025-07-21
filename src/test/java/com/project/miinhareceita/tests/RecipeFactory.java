package com.project.miinhareceita.tests;

import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.projection.RecipeProjections;
import com.project.miinhareceita.user.domain.User;

import java.time.Instant;

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


}