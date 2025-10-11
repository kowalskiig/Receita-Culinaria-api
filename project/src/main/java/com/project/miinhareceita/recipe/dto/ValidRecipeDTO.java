package com.project.miinhareceita.domains.recipe.dto;

import java.util.List;

public interface ValidRecipeDTO {

        String getTitle();
        String getShortDescription();
        String getInstructions();
        Integer getTimeMinutes();
        Integer getRendiment();
        String getUrlImg();
        List<RecipeIngredientsDTO> getItems();

}
