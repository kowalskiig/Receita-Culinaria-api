package com.project.miinhareceita.recipe.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor
public class UpdateRecipeDTO implements ValidRecipeDTO{


    private String title;


    private String shortDescription;


    private String instructions;


    private Integer timeMinutes;


    private Integer rendiment;



    private String urlImg;


    private List<RecipeIngredientsDTO> items = new ArrayList<>();



    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }

    @Override
    public String getInstructions() {
        return instructions;
    }

    @Override
    public Integer getTimeMinutes() {
        return timeMinutes;
    }

    @Override
    public Integer getRendiment() {
        return rendiment;
    }



    @Override
    public String getUrlImg() {
        return urlImg;
    }

    @Override
    public List<RecipeIngredientsDTO> getItems() {
        return items;
    }
}
