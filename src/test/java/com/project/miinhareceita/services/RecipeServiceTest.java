package com.project.miinhareceita.services;

import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.dto.RecipeMinDTO;
import com.project.miinhareceita.recipe.projection.RecipeProjections;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.recipe.service.RecipeService;
import com.project.miinhareceita.tests.RecipeFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class RecipeServiceTest {
    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    private Recipe recipe;
    private RecipeProjections recipeProjections;
    private RecipeMinDTO recipeMinDTO;
    private Pageable pageable;

    private List<RecipeProjections> recipeProjectionsList;
    private Page<RecipeProjections> recipeProjectionsPage;
    private List<RecipeMinDTO> recipeMinDTOList;

    @BeforeEach
    void setUp(){
        recipe = RecipeFactory.createRecipe();
        recipeMinDTO = new RecipeMinDTO(recipe);
        pageable = PageRequest.of(0, 10);

        recipeProjections = RecipeFactory.createRecipeProjections();
        recipeProjectionsList = new ArrayList<>();
        recipeProjectionsList.add(recipeProjections);

        recipeMinDTOList = new ArrayList<>();
        recipeMinDTOList.add(recipeMinDTO);

        recipeProjectionsPage = new PageImpl<>(recipeProjectionsList);

        Mockito.when(recipeRepository.searchRecipesByCategoriesIngredientsAndName(any(), any(), any(), any())).thenReturn(recipeProjectionsPage);

        Mockito.when(recipeRepository.searchRecipeByCategoryIngredient(any())).thenReturn(recipeMinDTOList);

    }
    @Test
    public void  searchRecipesByCategoriesIngredientsAndNameShouldReturnPageRecipeMinDtoWhenSucess(){
        Page<RecipeMinDTO> result = recipeService.searchRecipesByCategoriesIngredientsAndName("1", "2", "name", pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getNumberOfElements(), recipeMinDTOList.size());
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals(1, result.getNumberOfElements());
        Assertions.assertFalse(result.isEmpty());
    }
}
