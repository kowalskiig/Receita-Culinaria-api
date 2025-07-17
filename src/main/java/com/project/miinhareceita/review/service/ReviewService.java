package com.project.miinhareceita.review.service;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.review.domain.Review;
import com.project.miinhareceita.review.dto.InsertReviewDTO;
import com.project.miinhareceita.review.dto.ReviewDTO;
import com.project.miinhareceita.review.dto.ReviewValidDTO;
import com.project.miinhareceita.review.dto.UpdateReviewDTO;
import com.project.miinhareceita.review.projection.ReviewProjections;
import com.project.miinhareceita.review.repository.ReviewRepository;
import com.project.miinhareceita.shared.exceptions.DatabaseException;
import com.project.miinhareceita.shared.exceptions.ForbiddenException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.user.domain.User;
import com.project.miinhareceita.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;


    @Transactional(readOnly = true)
    public Page<ReviewDTO> findReviewByRecipeId(Long id, Pageable pageable) {

        List<ReviewProjections> reviews = reviewRepository.findReviewByRecipeId(id);

        List<ReviewDTO> result = new ArrayList<>();

        for(ReviewProjections projections : reviews){
            Review reviewE= new Review();

            reviewE.setId(projections.getId());
            reviewE.setNota(projections.getNota());
            reviewE.setDataReview(projections.getDataReview().toInstant());
            reviewE.setComment(projections.getComment());

            User user = userRepository.getReferenceById(projections.getUser_Id());
            Recipe recipe = recipeRepository.getReferenceById(projections.getRecipe_Id());

            reviewE.setUser(user);
            reviewE.setRecipes(recipe);

            result.add(new ReviewDTO(reviewE));
        }


        return new PageImpl<>(result, pageable, pageable.getPageSize());
    }

    @Transactional(readOnly = false)
    public ReviewDTO insertReview(Long recipeId, InsertReviewDTO dto){
        try{
            Review review = new Review();
            review.setRecipes(recipeRepository.getReferenceById(recipeId));

            review.setDataReview(Instant.now());
            review.setUser(authService.authenticated());

            copyDtoToEntity(review, dto);

            review = reviewRepository.save(review);
            return new ReviewDTO(review);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + recipeId);
        }



    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteReview(Long id){
            Review review = validationExistsReviewsId(id);
            validationReviewUserIdEqualsUserId(review);

            try {
                reviewRepository.deleteById(id);
            }
            catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Falha de integridade referencial");
            }
    }

    @Transactional
    public ReviewDTO updateReview(Long reviewId, UpdateReviewDTO dto){
            Review review = validationExistsReviewsId(reviewId);
            validationReviewUserIdEqualsUserId(review);

            review = reviewRepository.getReferenceById(reviewId);

            copyDtoToEntity(review,dto);

            review = reviewRepository.save(review);
            return new ReviewDTO(review);

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
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
    }



    private void validationReviewUserIdEqualsUserId(Review review){
        if(!authService.authenticated().getId().equals(review.getUser().getId())){
            throw new ForbiddenException("Não tem permissão para isso");
        }
    }

}
