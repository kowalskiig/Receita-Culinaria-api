package com.project.miinhareceita.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FavoriteInsertDTO {
    @NotNull(message = "O ID da receita não pode ser nulo")
    @Positive(message = "O ID da receita deve ser um número positivo")
    private Long recipeId;

    public FavoriteInsertDTO(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getRecipeId() {
        return recipeId;
    }
}

