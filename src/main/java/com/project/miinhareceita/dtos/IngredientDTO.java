package com.project.miinhareceita.dtos;

import com.project.miinhareceita.entities.Ingredients;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class IngredientDTO {
    private Long id;
    @NotBlank(message = "O campo é obrigatório")
    @Size(min = 2, message = "Requer pelo menos 2 caracteres")
    private String name;

    public IngredientDTO(Ingredients ingredient){
        id = ingredient.getId();
        name = ingredient.getName();
    }

    public IngredientDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
