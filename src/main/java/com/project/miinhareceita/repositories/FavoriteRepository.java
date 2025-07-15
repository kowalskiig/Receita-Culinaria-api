package com.project.miinhareceita.repositories;

import com.project.miinhareceita.dtos.FavoriteDTO;
import com.project.miinhareceita.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
}
