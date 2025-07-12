package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.RecipeDTO;
import com.project.miinhareceita.dtos.RecipeIngredientsDTO;
import com.project.miinhareceita.dtos.RecipeMinDTO;
import com.project.miinhareceita.entities.Ingredients;
import com.project.miinhareceita.entities.Recipe;
import com.project.miinhareceita.entities.RecipeIngredients;
import com.project.miinhareceita.projections.RecipeProjections;
import com.project.miinhareceita.repositories.IngredientsRepository;
import com.project.miinhareceita.repositories.RecipeRepository;
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


    @Transactional(readOnly = true)
    public Page<RecipeMinDTO> searchRecipesByCategoriesIngredientsAndName(String categoriesId, String ingredientsId, String name, Pageable pageable){

        List<Long> listCat= Arrays.asList();
        if(!"".equals(categoriesId)){
            listCat = Arrays.asList(categoriesId.split(",")).stream().map(Long::parseLong).toList();
        }
        List<Long> listIngred = Arrays.asList();
        if(!"".equals(ingredientsId)){
            listIngred = Arrays.asList(ingredientsId.split(",")).stream().map(Long::parseLong).toList();
        }

        Page<RecipeProjections> page = repository.searchRecipesByCategoriesIngredientsAndName(listCat, listIngred, name, pageable);
        List<Long> recipeId = page.map(RecipeProjections::getId).toList();

        List<Recipe> recipe = repository.searchRecipeByCategoryIngredient(recipeId);

        List<RecipeMinDTO> dto = recipe.stream().map(RecipeMinDTO::new).toList();

        return new PageImpl<>(dto, page.getPageable(), page.getTotalElements());
    }

    @Transactional(readOnly = false)
    public RecipeDTO insertRecipe(RecipeDTO dto) {

        Recipe recipe = new Recipe();
        copyDtoToEntity(recipe, dto);

        recipe.setPublicationDate(Instant.now());
        recipe.setUser(authService.authenticated());
        for(RecipeIngredientsDTO recipeIngredients: dto.getItems()){

                Ingredients ingredients = ingredientsRepository.findById(recipeIngredients.getIngredientId())
                        .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));

                RecipeIngredients ri = new RecipeIngredients(recipe, ingredients,
                        recipeIngredients.getQuantity(), recipeIngredients.getPrice());

                recipe.getIngredients().add(ri);
            }


        recipe = repository.save(recipe);

        return new RecipeDTO(recipe);
    }

    private void copyDtoToEntity(Recipe recipe,RecipeDTO dto){
        recipe.setId(dto.getId());
        recipe.setTitle(dto.getTitle());
        recipe.setShortDescription(dto.getShortDescription());
        recipe.setInstructions(dto.getInstructions());
        recipe.setTimeMinutes(dto.getTimeMinutes());
        recipe.setRendiment(dto.getRendiment());
        recipe.setUrlImg(dto.getUrlImg());

    }
}

