package com.project.miinhareceita.recipe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class InsertRecipeDTO implements ValidRecipeDTO{


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


    @NotBlank(message = "A URL da imagem é obrigatória!")
    private String urlImg;

    @NotEmpty(message = "Deve ter pelo menos 1 ingredient")
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
