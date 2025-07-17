package com.project.miinhareceita.recipe.service;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.ingredient.repository.IngredientsRepository;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import com.project.miinhareceita.recipe.dto.*;
import com.project.miinhareceita.recipe.projection.RecipeProjections;
import com.project.miinhareceita.recipe.repository.RecipeIngredientsRepository;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.shared.exceptions.ForbiddenException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipeService {

        private final RecipeRepository recipeRepository;
        private final AuthService authService;
        private final IngredientsRepository ingredientsRepository;
        private final RecipeIngredientsRepository recipeIngredientsRepository;

        public RecipeService(
                RecipeRepository recipeRepository,
                AuthService authService,
                IngredientsRepository ingredientsRepository,
                RecipeIngredientsRepository recipeIngredientsRepository
        ) {
            this.recipeRepository = recipeRepository;
            this.authService = authService;
            this.ingredientsRepository = ingredientsRepository;
            this.recipeIngredientsRepository = recipeIngredientsRepository;
        }



        @Transactional(readOnly = true)
    public Page<RecipeMinDTO> searchRecipesByCategoriesIngredientsAndName(String categoriesId, String ingredientsId, String name, Pageable pageable) {

        List<Long> catIds = verificationReceiveIdAndSendToList(categoriesId);
        List<Long> ingredIds = verificationReceiveIdAndSendToList(ingredientsId);

        Page<RecipeProjections> page = recipeRepository
                .searchRecipesByCategoriesIngredientsAndName(catIds, ingredIds, name, pageable);

        List<Long> recipeIds = page
                .map(RecipeProjections::getId).toList();

        List<RecipeMinDTO> result = recipeRepository.searchRecipeByCategoryIngredient(recipeIds);

        return new PageImpl<>(result, page.getPageable(), page.getTotalElements());
    }

    @Transactional(readOnly = false)
    public RecipeDTO insertRecipe(InsertRecipeDTO dto) {

        Recipe recipe = new Recipe();
        mapInsertDtoToEntity(recipe, dto);

        recipe.setPublicationDate(Instant.now());
        
        recipe.setUser(authService.authenticated());

        recipe = recipeRepository.save(recipe);

        addRecipeIngredientsToRecipe(recipe, dto.getItems());

        recipe = recipeRepository.save(recipe);

        return new RecipeDTO(recipe);
    }

    @Transactional
    public RecipeDTO findRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada"));

        return new RecipeDTO(recipe);
    }

    @Transactional(readOnly = false)
    public RecipeDTO updateRecipe(Long recipeId, UpdateRecipeDTO dto) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada"));

        userRecipeVerification(recipe);


        mapUpdateDtoToEntity(recipe, dto);

        recipe.setPublicationDate(Instant.now());

        if (!dto.getItems().isEmpty()) {
            recipeIngredientsRepository.deleteByRecipeId(recipe.getId());
            recipe.getIngredients().clear();

            addRecipeIngredientsToRecipe(recipe, dto.getItems());

        }

        recipe = recipeRepository.save(recipe);

        return new RecipeDTO(recipe);
    }


    private void userRecipeVerification(Recipe recipe){
        User user = authService.authenticated();

        if(!recipe.getUser().getId().equals(user.getId())){
            throw new ForbiddenException("Não tem acesso para isso");
        }
    }

    private void mapInsertDtoToEntity(Recipe recipe, InsertRecipeDTO dto) {
        recipe.setTitle(dto.getTitle());
        recipe.setShortDescription(dto.getShortDescription());
        recipe.setInstructions(dto.getInstructions());
        recipe.setTimeMinutes(dto.getTimeMinutes());
        recipe.setRendiment(dto.getRendiment());
        recipe.setUrlImg(dto.getUrlImg());
    }


    private void mapUpdateDtoToEntity(Recipe recipe, UpdateRecipeDTO dto) {
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            recipe.setTitle(dto.getTitle());
        }

        if (dto.getShortDescription() != null && !dto.getShortDescription().isBlank()) {
            recipe.setShortDescription(dto.getShortDescription());
        }

        if (dto.getInstructions() != null && !dto.getInstructions().isBlank()) {
            recipe.setInstructions(dto.getInstructions());
        }

        if (dto.getTimeMinutes() != null && dto.getTimeMinutes() > 0) {
            recipe.setTimeMinutes(dto.getTimeMinutes());
        }

        if (dto.getRendiment() != null && dto.getRendiment() > 0) {
            recipe.setRendiment(dto.getRendiment());
        }

        if (dto.getUrlImg() != null && !dto.getUrlImg().isBlank()) {
            recipe.setUrlImg(dto.getUrlImg());
        }
    }

    private List<Long> verificationReceiveIdAndSendToList(String idReceive) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(idReceive)) {
            list = Arrays.asList(idReceive.split(",")).stream().map(Long::parseLong).toList();
        }
        return list;
    }

    private void addRecipeIngredientsToRecipe(Recipe recipe, List<RecipeIngredientsDTO> items) {
        for (RecipeIngredientsDTO recipeIngredients : items) {
            Ingredients ingredient = ingredientsRepository.findById(recipeIngredients.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingrediente não encontrado"));

            RecipeIngredients ri = new RecipeIngredients(
                    recipe,
                    ingredient,
                    recipeIngredients.getQuantity(),
                    recipeIngredients.getPrice()
            );

            recipe.getIngredients().add(ri);

            recipeIngredientsRepository.save(ri);
        }
    }
}

