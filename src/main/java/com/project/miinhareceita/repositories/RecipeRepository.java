package com.project.miinhareceita.repositories;

import com.project.miinhareceita.entities.Recipe;
import com.project.miinhareceita.projections.RecipeProjections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(nativeQuery = true,value = """
                SELECT DISTINCT tb_recipe.id, tb_recipe.title
            FROM
                tb_recipe
            INNER JOIN tb_recipe_category ON tb_recipe_category.recipe_id = tb_recipe.id
            INNER JOIN tb_recipe_ingredients ON tb_recipe_ingredients.recipe_id = tb_recipe.id
            WHERE (:categoryIds IS NULL OR tb_recipe_category.category_id IN (:categoryIds))
                AND (:ingredientsId IS NULL OR tb_recipe_ingredients.ingredients_id IN (:ingredientsId))
            	AND (LOWER(tb_recipe.title) LIKE LOWER(CONCAT('%',:name,'%')))
            """
    , countQuery = """
            SELECT COUNT(*) FROM (
            SELECT DISTINCT tb_recipe.id, tb_recipe.title
            FROM
                tb_recipe
            INNER JOIN tb_recipe_category ON tb_recipe_category.recipe_id = tb_recipe.id
            INNER JOIN tb_recipe_ingredients ON tb_recipe_ingredients.recipe_id = tb_recipe.id
             WHERE (:categoryIds IS NULL OR tb_recipe_category.category_id IN (:categoryIds))
                AND (:ingredientsId IS NULL OR tb_recipe_ingredients.ingredients_id IN (:ingredientsId))
            	AND (LOWER(tb_recipe.title) LIKE LOWER(CONCAT('%',:name,'%')))
            	)
            """)
    Page<RecipeProjections> searchRecipesByCategoriesIngredientsAndName(List<Long> categoryIds, List<Long> ingredientsId, String name, Pageable pageable);

    @Query("SELECT obj FROM Recipe obj  "
            + "WHERE obj.id IN :recipeIds")
    List<Recipe> searchRecipeByCategoryIngredient(List<Long> recipeIds);
}
