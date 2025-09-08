package com.project.miinhareceita.services;

import com.project.miinhareceita.domains.user.auth.service.AuthService;
import com.project.miinhareceita.domains.ingredient.domain.Ingredients;
import com.project.miinhareceita.domains.ingredient.repository.IngredientsRepository;
import com.project.miinhareceita.domains.recipe.domain.Recipe;
import com.project.miinhareceita.domains.recipe.domain.RecipeIngredients;
import com.project.miinhareceita.domains.recipe.dto.*;
import com.project.miinhareceita.domains.recipe.projection.RecipeProjections;
import com.project.miinhareceita.domains.recipe.repository.RecipeIngredientsRepository;
import com.project.miinhareceita.domains.recipe.repository.RecipeRepository;
import com.project.miinhareceita.domains.recipe.service.RecipeService;
import com.project.miinhareceita.shared.exceptions.ForbiddenException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.tests.IngredientFactory;
import com.project.miinhareceita.tests.RecipeFactory;
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
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class RecipeServiceTest {
    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeIngredientsRepository recipeIngredientsRepository;
    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private AuthService authService;

    @Mock
    private IngredientsRepository ingredientsRepository;

    private User user;
    private Ingredients ingredients;
    private Recipe recipe;
    private RecipeIngredients recipeIngredients;
    private RecipeProjections recipeProjections;
    private RecipeMinDTO recipeMinDTO;
    private Pageable pageable;

    private UpdateRecipeDTO updateRecipeDTO;


    private Long existingIngredientId, nonExistingIngredientId, existingRecipeId, nonExistingRecipeId;

    private List<RecipeProjections> recipeProjectionsList;
    private Page<RecipeProjections> recipeProjectionsPage;
    private List<RecipeMinDTO> recipeMinDTOList;

    @BeforeEach
    void setUp(){
        recipe = RecipeFactory.createRecipe();
        user = UserFactory.createUser();

        recipeMinDTO = new RecipeMinDTO(recipe);
        recipeIngredients = new RecipeIngredients(recipe, ingredients, 1 ,2.0);


        pageable = PageRequest.of(0, 10);



        existingIngredientId =1L;
        nonExistingIngredientId = 2L;

        existingRecipeId =1L;
        nonExistingRecipeId =2L;

        ingredients = IngredientFactory.createIngredient(existingIngredientId, "tomate");

        recipeProjections = RecipeFactory.createRecipeProjections();
        recipeProjectionsList = new ArrayList<>();
        recipeProjectionsList.add(recipeProjections);

        recipeMinDTOList = new ArrayList<>();
        recipeMinDTOList.add(recipeMinDTO);

        updateRecipeDTO = RecipeFactory.createUpdateRecipeDto();

        recipeProjectionsPage = new PageImpl<>(recipeProjectionsList);

        Mockito.when(recipeRepository.searchRecipesByCategoriesIngredientsAndName(any(), any(), any(), any())).thenReturn(recipeProjectionsPage);

        Mockito.when(recipeRepository.searchRecipeByCategoryIngredient(any())).thenReturn(recipeMinDTOList);

        Mockito.when(recipeRepository.save(any())).thenReturn(recipe);

        Mockito.when(authService.authenticated()).thenReturn(user);

        Mockito.when(ingredientsRepository.findById(existingIngredientId)).thenReturn(Optional.of(ingredients));
        Mockito.when(ingredientsRepository.findById(nonExistingIngredientId)).thenReturn(Optional.empty());

        Mockito.when(recipeIngredientsRepository.save(any())).thenReturn(recipeIngredients);

        Mockito.when(recipeRepository.findById(existingRecipeId)).thenReturn(Optional.of(recipe));
        Mockito.when(recipeRepository.findById(nonExistingRecipeId)).thenReturn(Optional.empty());






    }
    @Test
    public void  searchRecipesByCategoriesIngredientsAndNameShouldReturnPageRecipeMinDtoWhenSucess(){
        Page<RecipeMinDTO> result = recipeService.searchRecipesByCategoriesIngredientsAndName("", "2,2", "name", pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getNumberOfElements(), recipeMinDTOList.size());
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(1, result.getNumberOfElements());
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void insertRecipeShouldReturnRecipeDtoWhenSucess(){

        RecipeIngredientsDTO recipeIngredientsDTO = new RecipeIngredientsDTO(1L, existingIngredientId, 1, 2.0);

        InsertRecipeDTO dto = RecipeFactory.createInsertRecipeDto();
        dto.getItems().add(recipeIngredientsDTO);




        RecipeDTO result = recipeService.insertRecipe(dto);

        Assertions.assertNotNull(result);

    }

    @Test
    public void insertRecipeShouldReturnResourceNotFoundExceptionWhenIngredientIdDoesNotExists(){
        RecipeIngredientsDTO recipeIngredientsDTO = new RecipeIngredientsDTO(1L, nonExistingIngredientId, 1, 2.0);

        InsertRecipeDTO dto = RecipeFactory.createInsertRecipeDto();
        dto.getItems().add(recipeIngredientsDTO);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {recipeService.insertRecipe(dto);
        });
    }
    @Test
    public void findRecipeByIdShouldReturnRecipeDTOWhenSucess(){
        RecipeDTO result = recipeService.findRecipeById(existingRecipeId);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
        Mockito.verify(recipeRepository, times(1)).findById(existingRecipeId);
    }

    @Test
    public void findRecipeByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            recipeService.findRecipeById(nonExistingRecipeId);
        });
        Mockito.verify(recipeRepository, times(1)).findById(nonExistingRecipeId);
    }

    @Test
    public void updateRecipeShouldReturnRecipeDTOWhenAllSucess(){

        RecipeDTO result = recipeService.updateRecipe(existingRecipeId ,updateRecipeDTO);

        Assertions.assertNotNull(result);
        Mockito.verify(authService, times(1)).authenticated();
        Mockito.verify(recipeRepository, times(1)).findById(existingRecipeId);
        Mockito.verify(recipeRepository, times(1)).save(any());

        };

    @Test
    public void updateRecipeShouldReturnForbiddenExceptionWhenUserIdDiferentOfRecipeId(){
        Long existingRecipeWithUserId = 3L;

        User recipeUser = UserFactory.createUser();
        recipeUser.setId(2L);


        recipe.setId(3L);
        recipe.setUser(recipeUser);


        user.setId(1L);


        Mockito.when(recipeRepository.findById(existingRecipeWithUserId)).thenReturn(Optional.of(recipe));


        Mockito.when(authService.authenticated()).thenReturn(user);

        Assertions.assertThrows(ForbiddenException.class, () -> {
            recipeService.updateRecipe(existingRecipeWithUserId, updateRecipeDTO);
        });
    }






}


