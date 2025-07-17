package com.project.miinhareceita.services;

import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.ingredient.repository.IngredientsRepository;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.domain.RecipeIngredients;
import com.project.miinhareceita.recipe.dto.RecipeDTO;
import com.project.miinhareceita.recipe.dto.RecipeIngredientsDTO;
import com.project.miinhareceita.recipe.dto.RecipeMinDTO;
import com.project.miinhareceita.recipe.projection.RecipeProjections;
import com.project.miinhareceita.recipe.repository.RecipeIngredientsRepository;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private RecipeIngredientsRepository riRepository;


    @Transactional(readOnly = true)
    public Page<RecipeMinDTO> searchRecipesByCategoriesIngredientsAndName(String categoriesId, String ingredientsId, String name, Pageable pageable){

        List<Long> listCat= Arrays.asList();
        verificationReceiveIdAndSendToList(listCat, categoriesId);

        List<Long> listIngred = Arrays.asList();
        verificationReceiveIdAndSendToList(listIngred, ingredientsId);

        Page<RecipeProjections> page = repository
                .searchRecipesByCategoriesIngredientsAndName(listCat, listIngred, name, pageable);

        List<Long> recipeId = page
                .map(RecipeProjections::getId).toList();

        List<RecipeMinDTO> recipe = repository.searchRecipeByCategoryIngredient(recipeId);


        return new PageImpl<>(recipe, page.getPageable(), page.getTotalElements());
    }

    @Transactional(readOnly = false)
    public RecipeDTO insertRecipe(RecipeDTO dto) {

        Recipe recipe = new Recipe();
        copyDtoToEntity(recipe, dto);

        recipe.setPublicationDate(Instant.now());
        recipe.setUser(authService.authenticated());
        recipe = repository.save(recipe);
        for(RecipeIngredientsDTO recipeIngredients: dto.getItems()){

                Ingredients ingredients = ingredientsRepository.findById(recipeIngredients.getIngredientId())
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

                RecipeIngredients ri = new RecipeIngredients(recipe, ingredients,
                        recipeIngredients.getQuantity(), recipeIngredients.getPrice());
                recipe.getIngredients().add(ri);

                riRepository.save(ri);
            }


        recipe = repository.save(recipe);

        return new RecipeDTO(recipe);
    }

    @Transactional
    public RecipeDTO findRecipeById(Long recipeId){
        Recipe recipe =  repository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        return new RecipeDTO(recipe);
    }

    @Transactional(readOnly = false)
    public RecipeDTO updateRecipe(Long id ,RecipeDTO dto) {
        RecipeIngredients ri = new RecipeIngredients();
        Recipe recipe = repository.getReferenceById(id);
        copyDtoToEntity(recipe, dto);

        recipe.setPublicationDate(Instant.now());
        if(dto.getItems() != null){
            riRepository.deleteByRecipeId(recipe.getId());
            recipe.getIngredients().clear();
            for(RecipeIngredientsDTO recipeIngredients: dto.getItems()){

                Ingredients ingredients = ingredientsRepository.findById(recipeIngredients.getIngredientId())
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

                ri = new RecipeIngredients(recipe, ingredients,
                        recipeIngredients.getQuantity(), recipeIngredients.getPrice());

                riRepository.save(ri);
                recipe.getIngredients().add(ri);
            }

        }
        recipe = repository.save(recipe);

        return new RecipeDTO(recipe);
    }


    private void copyDtoToEntity(Recipe recipe, RecipeDTO dto){
        recipe.setTitle(dto.getTitle());
        recipe.setShortDescription(dto.getShortDescription());
        recipe.setInstructions(dto.getInstructions());
        recipe.setTimeMinutes(dto.getTimeMinutes());
        recipe.setRendiment(dto.getRendiment());
        recipe.setUrlImg(dto.getUrlImg());

    }

    private void verificationReceiveIdAndSendToList(List<Long> list, String idReceive){
        if(!"".equals(idReceive)){
            list = Arrays.asList(idReceive.split(",")).stream().map(Long::parseLong).toList();
        }
    }
}

