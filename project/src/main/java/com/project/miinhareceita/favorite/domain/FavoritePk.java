package com.project.miinhareceita.domains.favorite.domain;

import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.domains.user.domain.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

@Embeddable
public class FavoritePk {

        @ManyToOne
        @JoinColumn(name = "recipe_id")
        private Recipe recipe;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        public FavoritePk() {
        }

        public Recipe getRecipe() {
                return recipe;
        }

        public void setRecipe(Recipe recipe) {
                this.recipe = recipe;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                FavoritePk that = (FavoritePk) o;
                return Objects.equals(recipe, that.recipe) && Objects.equals(user, that.user);
        }

        @Override
        public int hashCode() {
                return Objects.hash(recipe, user);
        }
}