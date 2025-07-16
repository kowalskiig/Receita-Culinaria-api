package com.project.miinhareceita.repositories;

import com.project.miinhareceita.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("""
            SELECT obj
            FROM Favorite obj
            WHERE obj.id.user.id = :userId
            """)
    List<Favorite> findFavoritesByUserId(Long userId);

    @Query("""
    SELECT COUNT(obj) > 0
    FROM Favorite obj
    WHERE obj.id.user.id = :userId AND obj.id.recipe.id = :recipeId
    """)
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

}
