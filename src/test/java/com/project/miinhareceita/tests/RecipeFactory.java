package com.project.miinhareceita.tests;

import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.user.domain.User;

import java.time.Instant;

public class RecipeFactory {
    public static Recipe createRecipe(){
        User user = UserFactory.createUser();
        return new Recipe(user, "imgUrl", Instant.now(), 5, "instructions", 10, "shortDescription", "title", 1L  );
    }
}
