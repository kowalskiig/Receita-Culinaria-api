package com.project.miinhareceita.dtos;

import com.project.miinhareceita.review.domain.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class ReviewDTO {
    private Long id;

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Integer nota;
    @NotBlank
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
