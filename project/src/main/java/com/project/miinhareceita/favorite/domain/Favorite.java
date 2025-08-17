package com.project.miinhareceita.favorite.domain;

import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.user.domain.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(name = "tb_favorite")
public class Favorite {
    @EmbeddedId
    private FavoritePk id = new FavoritePk();

     public Favorite(Recipe recipe, User user) {
        id.setRecipe(recipe);
        id.setUser(user);
    }

    public FavoritePk getId() {
        return id;
    }

    public void setId(FavoritePk id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(id, favorite.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
