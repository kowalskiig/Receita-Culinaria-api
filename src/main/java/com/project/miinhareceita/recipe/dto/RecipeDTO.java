package com.project.miinhareceita.recipe.dto;


import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import com.project.miinhareceita.user.dto.UserMinDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
public class RecipeDTO {
    private Long id;

    private String title;


    private String shortDescription;


    private String instructions;


    private Integer timeMinutes;


    private Integer rendiment;


    private Instant publicationDate;


    private String urlImg;


    private List<RecipeIngredientsDTO> items = new ArrayList<>();

    private UserMinDTO user;



    public RecipeDTO (Recipe recipe){
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.shortDescription = recipe.getShortDescription();
        this.instructions = recipe.getInstructions();
        this.timeMinutes = recipe.getTimeMinutes();
        this.rendiment = recipe.getRendiment();
        this.publicationDate = recipe.getPublicationDate();
        this.urlImg = recipe.getUrlImg();
        for (RecipeIngredients recipeIngredients: recipe.getIngredients()){
            items.add(new RecipeIngredientsDTO(recipeIngredients));
        }
        this.user = new UserMinDTO(recipe.getUser());
    }


}
