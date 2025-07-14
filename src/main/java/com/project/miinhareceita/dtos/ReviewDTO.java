package com.project.miinhareceita.dtos;

import com.project.miinhareceita.entities.Review;
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
    private RecipeMinDTO recipe;
    private UserMinDTO user;

    public ReviewDTO(Review review){
        id = review.getId();
        nota = review.getNota();
        comment = review.getComment();
        data_review = review.getDataReview();
        recipe = new RecipeMinDTO(review.getRecipes());
        user = new UserMinDTO(review.getUser());
    }
}
