package com.project.miinhareceita.review.repository;

import com.project.miinhareceita.review.domain.Review;
import com.project.miinhareceita.review.projection.ReviewProjections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT new com.project.miinhareceita.review.projection.ReviewProjections(obj.id, obj.nota, obj.dataReview, obj.comment, obj.user.id, obj.recipes.id) " +
            "FROM Review obj WHERE obj.recipes.id = :recipeId")
    Page<ReviewProjections> findReviewsByRecipeId(Long recipeId, Pageable pageable);
}

