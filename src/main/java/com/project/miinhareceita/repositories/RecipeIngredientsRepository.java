package com.project.miinhareceita.repositories;

import com.project.miinhareceita.entities.RecipeIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredients, Long> {

    @Modifying
    @Query(nativeQuery = true, value = """
    DELETE
    FROM tb_recipe_ingredients
    WHERE tb_recipe_ingredients.recipe_id = :id
    """)
    void deleteByRecipeId(Long id);
}
