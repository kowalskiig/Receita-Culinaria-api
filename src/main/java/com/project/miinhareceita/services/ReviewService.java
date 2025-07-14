package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.ReviewDTO;
import com.project.miinhareceita.entities.Recipe;
import com.project.miinhareceita.entities.Review;
import com.project.miinhareceita.entities.User;
import com.project.miinhareceita.projections.ReviewProjections;
import com.project.miinhareceita.repositories.RecipeRepository;
import com.project.miinhareceita.repositories.ReviewRepository;
import com.project.miinhareceita.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;


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

}
