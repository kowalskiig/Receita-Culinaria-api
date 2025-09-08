package com.project.miinhareceita.domains.ingredient.domain;


import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name ="tb_ingredients")
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @OneToMany(mappedBy = "id.ingredients")
    private List<RecipeIngredients> ingredients = new ArrayList<>();

    public Ingredients(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
