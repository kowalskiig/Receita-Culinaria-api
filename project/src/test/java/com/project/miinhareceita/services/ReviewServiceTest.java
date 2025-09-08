package com.project.miinhareceita.services;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.review.domain.Review;
import com.project.miinhareceita.review.dto.InsertReviewDTO;
import com.project.miinhareceita.review.dto.ReviewDTO;
import com.project.miinhareceita.review.dto.UpdateReviewDTO;
import com.project.miinhareceita.review.repository.ReviewRepository;
import com.project.miinhareceita.review.service.ReviewService;
import com.project.miinhareceita.shared.exceptions.ForbiddenException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.tests.RecipeFactory;
import com.project.miinhareceita.tests.ReviewFactory;
import com.project.miinhareceita.tests.UserFactory;
import com.project.miinhareceita.domains.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private AuthService authService;

    private Page<Review> reviewPage;
    private List<Review> listReview;

    private Review review;

    private Long existingRecipeId, nonExistingRecipeId, existingReviewId;

    private Pageable pageable;

    private Recipe recipe;
    private User user;


    private InsertReviewDTO insertReviewDTO;
    private UpdateReviewDTO updateReviewDTO;

    @BeforeEach
    void setUp(){

        recipe = RecipeFactory.createRecipe();
        user = UserFactory.createUser();

        listReview = new ArrayList<>();

        review = ReviewFactory.createReview();
        listReview.add(review);
        reviewPage = new PageImpl<>(listReview);
        existingRecipeId =1L;
        nonExistingRecipeId =2L;
        existingReviewId =1L;
        pageable = PageRequest.of(0, 10);

        insertReviewDTO = new InsertReviewDTO(3, "Boa");
        updateReviewDTO = new UpdateReviewDTO(3, "Boa");

        Mockito.when(reviewRepository.findReviewsByRecipeId(any(), any())).thenReturn(reviewPage);

        Mockito.when(recipeRepository.findById(existingRecipeId)).thenReturn(Optional.of(recipe));
        Mockito.when(recipeRepository.findById(nonExistingRecipeId)).thenReturn(Optional.empty());

        Mockito.when(reviewRepository.findById(existingRecipeId)).thenReturn(Optional.of(review));
        Mockito.when(reviewRepository.findById(nonExistingRecipeId)).thenReturn(Optional.empty());

        Mockito.when(authService.authenticated()).thenReturn(user);

        Mockito.when(reviewRepository.save(any())).thenReturn(review);

        Mockito.doNothing().when(reviewRepository).deleteById(existingReviewId);
    }

    @Test
    void findReviewByRecipeIdShouldReturnPageReviewDTOWhenSucess(){
        Page<ReviewDTO> result = reviewService.findReviewByRecipeId(existingRecipeId, pageable);

        Assertions.assertNotNull(result);
    }

    @Test
    void insertReviewShouldReturnReviewDTOWhenSucess(){
        ReviewDTO result = reviewService.insertReview(existingRecipeId, insertReviewDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    void insertReviewShouldReturnResourceNotFoundWhenRecipeDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.insertReview(nonExistingRecipeId, insertReviewDTO);
        });
    }

    @Test
    void deleteReviewShouldReturnNothingWhenSucess(){
        Assertions.assertDoesNotThrow(() -> {
            reviewService.deleteReview(existingReviewId);
        });
    }

    @Test
    void deleteReviewShouldReturnResourceNotFoundWhenReviewDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            reviewService.deleteReview(nonExistingRecipeId);
        });
    }

    @Test
    void updateReviewShouldReturnReviewDTOWhenSucess(){
        ReviewDTO result = reviewService.updateReview(existingReviewId, updateReviewDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    void updateReviewShouldThrowForbiddenExceptionWhenUserDoesNotOwnReview(){
        User otherUser = UserFactory.createUser();
        otherUser.setId(2L);

        Review otherUserReview = ReviewFactory.createReview();
        otherUserReview.setUser(otherUser);

        Mockito.when(reviewRepository.findById(any())).thenReturn(Optional.of(otherUserReview));

        Mockito.when(authService.authenticated()).thenReturn(user);

        Assertions.assertThrows(ForbiddenException.class, () -> {
            reviewService.updateReview(any(), new UpdateReviewDTO(3, "Novo comentário"));
        });
    }

}
