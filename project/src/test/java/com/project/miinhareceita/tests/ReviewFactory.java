package com.project.miinhareceita.tests;

import com.project.miinhareceita.domains.recipe.domain.Recipe;
import com.project.miinhareceita.domains.review.domain.Review;
import com.project.miinhareceita.domains.user.domain.User;

import java.time.Instant;

public class ReviewFactory {
    public static Review createReview(){
        User user = UserFactory.createUser();
        Recipe recipe = RecipeFactory.createRecipe();
        return new Review(1L, user, recipe, Instant.now(), "title", 4);
    }

    public static Review createReviewWithOutData(){
        return new Review();
    }



    }
