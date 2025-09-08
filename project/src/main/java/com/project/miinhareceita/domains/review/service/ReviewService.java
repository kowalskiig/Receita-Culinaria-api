package com.project.miinhareceita.domains.review.service;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.domains.recipe.domain.Recipe;
import com.project.miinhareceita.domains.recipe.repository.RecipeRepository;
import com.project.miinhareceita.domains.review.domain.Review;
import com.project.miinhareceita.domains.review.dto.InsertReviewDTO;
import com.project.miinhareceita.domains.review.dto.ReviewDTO;
import com.project.miinhareceita.domains.review.dto.ReviewValidDTO;
import com.project.miinhareceita.domains.review.dto.UpdateReviewDTO;
import com.project.miinhareceita.domains.review.repository.ReviewRepository;
import com.project.miinhareceita.shared.exceptions.ForbiddenException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.domains.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    public ReviewService(
            ReviewRepository reviewRepository,
            RecipeRepository recipeRepository,
            UserRepository userRepository,
            AuthService authService
    ) {
        this.reviewRepository = reviewRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }


    @Transactional(readOnly = true)
    public Page<ReviewDTO> findReviewByRecipeId(Long id, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findReviewsByRecipeId(id, pageable);

        return reviews.map(ReviewDTO::new);
        }


    @Transactional(readOnly = false)
    public ReviewDTO insertReview(Long recipeId, InsertReviewDTO dto){

            Review review = new Review();

            review.setRecipes(recipeExists(recipeId));
            review.setDataReview(Instant.now());
            review.setUser(authService.authenticated());

        copyDtoToEntity(review, dto);

            return new ReviewDTO(reviewRepository.save(review));
        }


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteReview(Long id){
            Review review = validationExistsReviewsId(id);
            validationReviewUserIdEqualsUserId(review);


                reviewRepository.deleteById(id);

    }

    @Transactional
    public ReviewDTO updateReview(Long reviewId, UpdateReviewDTO dto){
            Review review = validationExistsReviewsId(reviewId);
            validationReviewUserIdEqualsUserId(review);

            copyDtoToEntity(review,dto);

            return new ReviewDTO(reviewRepository.save(review));

    }



    private void copyDtoToEntity(Review review, ReviewValidDTO dto){
        if (dto.getNota() != null && dto.getNota() > 0) {
            review.setNota(dto.getNota());
        }

        if (dto.getComment() != null && !dto.getComment().isBlank()) {
            review.setComment(dto.getComment());
        }
    }

    private Review validationExistsReviewsId(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }



    private void validationReviewUserIdEqualsUserId(Review review){
        if(!authService.authenticated().getId().equals(review.getUser().getId())){
            throw new ForbiddenException("You are not allowed to do this");
        }
    }

    private Recipe recipeExists(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));
    }

}
