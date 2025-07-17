package com.project.miinhareceita.recipe.dto;


import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import com.project.miinhareceita.user.domain.User;
import com.project.miinhareceita.user.dto.UserMinDTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;



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

    public RecipeDTO(){

    }

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

    public Long getId() {
        return id;
    }

    public UserMinDTO getUser() {
        return user;
    }
    public UserMinDTO setUser(User user){
        return this.user = new UserMinDTO(user);
    }

    public List<RecipeIngredientsDTO> getItems() {
        return items;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public Integer getRendiment() {
        return rendiment;
    }

    public Integer getTimeMinutes() {
        return timeMinutes;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getTitle() {
        return title;
    }
}
