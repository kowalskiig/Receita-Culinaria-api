package com.project.miinhareceita.dtos;

import com.project.miinhareceita.entities.Recipe;
import lombok.Getter;

import java.time.Instant;

@Getter
public class RecipeMinDTO {
    private Long id;
    private String title;
    private Integer timeMinutes;
    private Instant publicationDate;
    private String urlImg;


    public RecipeMinDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.timeMinutes = recipe.getTimeMinutes();
        this.publicationDate = recipe.getPublicationDate();
        this.urlImg = recipe.getUrlImg();
    }

}
