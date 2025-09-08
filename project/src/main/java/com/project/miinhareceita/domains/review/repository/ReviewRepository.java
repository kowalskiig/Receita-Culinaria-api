package com.project.miinhareceita.domains.review.repository;

import com.project.miinhareceita.domains.review.domain.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN FETCH r.user u JOIN FETCH r.recipes rec WHERE rec.id = :recipeId")
    Page<Review> findReviewsByRecipeId(Long recipeId, Pageable pageable);

}

