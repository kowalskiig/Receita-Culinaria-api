package com.project.miinhareceita.dtos;


import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import com.project.miinhareceita.user.domain.User;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;



public class RecipeDTO {
    private Long id;
    @NotBlank(message = "O título é obrigatório!")
    @Size(min = 3, max = 100, message = "O título deve ter entre {min} e {max} caracteres.")
    private String title;

    @NotBlank(message = "A descrição curta é obrigatória!")
    @Size(min = 10, max = 255, message = "A descrição curta deve ter entre {min} e {max} caracteres.")
    private String shortDescription;

    @NotBlank(message = "As instruções são obrigatórias!")
    @Size(min = 20, message = "As instruções devem ter no mínimo {min} caracteres.")
    private String instructions;

    @NotNull(message = "O tempo de preparo é obrigatório!")
    private Integer timeMinutes;

    @NotNull(message = "O rendimento é obrigatório!")
    private Integer rendiment;

    @Future(message = "Não deve estar no futuro")
    private Instant publicationDate;

    @NotBlank(message = "A URL da imagem é obrigatória!")
    private String urlImg;

    @NotEmpty(message = "Deve ter pelo menos 1 ingredient")
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
