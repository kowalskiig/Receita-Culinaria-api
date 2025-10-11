package com.project.miinhareceita.domains.ingredient.dto;

import com.project.miinhareceita.domains.ingredient.domain.Ingredients;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class IngredientDTO {
    private Long id;

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
