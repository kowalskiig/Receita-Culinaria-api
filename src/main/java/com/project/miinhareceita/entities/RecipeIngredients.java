package com.project.miinhareceita.entities;

import com.project.miinhareceita.entities.entitiespk.RecipeIngredientsPk;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_recipe_ingredients")
public class RecipeIngredients {
    @EmbeddedId
    private RecipeIngredientsPk id = new RecipeIngredientsPk();

    private Integer quantity;
    private Double price;
}
