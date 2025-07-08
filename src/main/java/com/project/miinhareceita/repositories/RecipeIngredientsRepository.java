package com.project.miinhareceita.repositories;

import com.project.miinhareceita.entities.Category;
import com.project.miinhareceita.entities.RecipeIngredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientsRepository extends JpaRepository<RecipeIngredients, Long> {
}
