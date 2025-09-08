package com.project.miinhareceita.review.dto;

import com.project.miinhareceita.recipe.dto.RecipeReviewDTO;
import com.project.miinhareceita.review.domain.Review;
import com.project.miinhareceita.domains.user.dto.UserMinDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class ReviewDTO {
    private Long id;

    private Integer nota;
    private String comment ;
    private Instant data_review;
    private RecipeReviewDTO recipe;
    private UserMinDTO user;

    public ReviewDTO(Review review){
        id = review.getId();
        nota = review.getNota();
        comment = review.getComment();
        data_review = review.getDataReview();
        recipe = new RecipeReviewDTO(review.getRecipes());
        user = new UserMinDTO(review.getUser());
    }
}
