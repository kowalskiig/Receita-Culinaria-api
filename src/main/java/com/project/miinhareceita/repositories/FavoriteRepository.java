package com.project.miinhareceita.repositories;

import com.project.miinhareceita.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {


    @EntityGraph(attributePaths = {"id.recipe"})
    @Query("""
            SELECT obj
            FROM Favorite obj
            INNER JOIN obj.id.user u
            INNER JOIN obj.id.recipe r
            WHERE obj.id.user.id = :userId
            """)
    List<Favorite> findFavoritesByUserId(Long userId);

    @EntityGraph(attributePaths = {"id.recipe", "id.recipe.user"})
    @Query("""
    SELECT COUNT(obj) > 0
    FROM Favorite obj
    INNER JOIN obj.id.user u
    INNER JOIN obj.id.recipe r
    WHERE obj.id.user.id = :userId AND obj.id.recipe.id = :recipeId
    """)
    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    @Modifying
    @Query(nativeQuery = true, value = """
    DELETE
    FROM tb_favorite
    WHERE tb_favorite.recipe_id = :recipeId AND tb_favorite.user_id = :userId
    """)
    void deleteByRecipeIdAndUser(Long recipeId, Long userId);
}
