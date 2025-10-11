package com.project.miinhareceita.domains.recipe.dto;

import com.project.miinhareceita.domains.recipe.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class RecipeMinDTO {
    private Long id;
    private String title;
    private Integer timeMinutes;
    private Instant publicationDate;
    private String urlImg;
    private Double media;


    public RecipeMinDTO(Long id, String title, Integer timeMinutes, Instant publicationDate, String urlImg, Double media) {
        this.id = id;
        this.title = title;
        this.timeMinutes = timeMinutes;
        this.publicationDate = publicationDate;
        this.urlImg = urlImg;
        this.media = media;
    }


    public RecipeMinDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.timeMinutes = recipe.getTimeMinutes();
        this.publicationDate = recipe.getPublicationDate();
        this.urlImg = recipe.getUrlImg();

    }

}
