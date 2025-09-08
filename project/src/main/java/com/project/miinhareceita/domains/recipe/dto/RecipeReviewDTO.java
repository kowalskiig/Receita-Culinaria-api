package com.project.miinhareceita.domains.recipe.dto;

import com.project.miinhareceita.domains.recipe.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RecipeReviewDTO {

    private Long id;
    private String title;

    public RecipeReviewDTO (Recipe recipe){
        id = recipe.getId();
        title = recipe.getTitle();
    }
}
