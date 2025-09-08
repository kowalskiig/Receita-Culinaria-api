package com.project.miinhareceita.domains.recipe.domain;

import com.project.miinhareceita.domains.ingredient.domain.Ingredients;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class RecipeIngredientsPk {

        @ManyToOne
        @JoinColumn(name = "recipe_id")
        private Recipe recipe;

        @ManyToOne
        @JoinColumn(name = "ingredients_id")
        private Ingredients ingredients;

        public RecipeIngredientsPk() {
        }


        public Recipe getRecipe() {
                return recipe;
        }

        public void setRecipe(Recipe recipe) {
                this.recipe = recipe;
        }

        public Ingredients getIngredients() {
                return ingredients;
        }

        public void setIngredients(Ingredients ingredients) {
                this.ingredients = ingredients;
        }

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                RecipeIngredientsPk that = (RecipeIngredientsPk) o;
                return Objects.equals(recipe, that.recipe) && Objects.equals(ingredients, that.ingredients);
        }

        @Override
        public int hashCode() {
                return Objects.hash(recipe, ingredients);
        }
}