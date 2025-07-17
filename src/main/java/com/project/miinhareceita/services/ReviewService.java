package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.ReviewDTO;
import com.project.miinhareceita.projections.ReviewProjections;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.repositories.RecipeRepository;
import com.project.miinhareceita.repositories.ReviewRepository;
import com.project.miinhareceita.repositories.UserRepository;
import com.project.miinhareceita.review.domain.Review;
import com.project.miinhareceita.services.exceptions.DatabaseException;
import com.project.miinhareceita.services.exceptions.ForbiddenException;
import com.project.miinhareceita.services.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.user.domain.User;
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
    private ReviewRepository repository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;


    @Transactional(readOnly = true)
    public Page<ReviewDTO> findReviewByRecipeId(Long id, Pageable pageable) {
        List<ReviewProjections> reviews = repository.findReviewByRecipeId(id);

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
    public ReviewDTO insertReview(Long recipeId, ReviewDTO dto){
        try{

            Review review = new Review();
            review.setRecipes(recipeRepository.getReferenceById(recipeId));

            review.setDataReview(Instant.now());
            review.setUser(authService.authenticated());
            copyToEntity(review, dto);

            review = repository.save(review);
            return new ReviewDTO(review);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + recipeId);
        }



    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteReview(Long id){
        Review review = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
            if(!authService.authenticated().getId().equals(review.getUser().getId())){
                throw new ForbiddenException("Não tem permissão para isso");
            }

            try {
                repository.deleteById(id);
            }
            catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Falha de integridade referencial");
            }
    }

    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO dto){
        Review review = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        if(!authService.authenticated().getId().equals(review.getUser().getId())){
            throw new ForbiddenException("Não tem permissão para isso");
        }
            review = repository.getReferenceById(id);
            copyToEntity(review,dto);
            review = repository.save(review);
            return new ReviewDTO(review);

    }


    private void copyToEntity(Review review, ReviewDTO dto){
        review.setNota(dto.getNota());
        review.setComment(dto.getComment());
    }

}
