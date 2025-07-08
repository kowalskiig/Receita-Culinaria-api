package com.project.miinhareceita.entities.entitiespk;

import com.project.miinhareceita.entities.Ingredients;
import com.project.miinhareceita.entities.Recipe;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class RecipeIngredientsPk {

        @ManyToOne
        @JoinColumn(name = "recipe_id")
        private Recipe recipe;

        @ManyToOne
        @JoinColumn(name = "ingredients_id")
        private Ingredients ingredients;



    }
