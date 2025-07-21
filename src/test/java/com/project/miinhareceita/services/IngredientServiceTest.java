package com.project.miinhareceita.services;

import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.ingredient.dto.IngredientDTO;
import com.project.miinhareceita.ingredient.dto.InsertIngredientDTO;
import com.project.miinhareceita.ingredient.projection.IngredientProjection;
import com.project.miinhareceita.ingredient.repository.IngredientsRepository;
import com.project.miinhareceita.ingredient.service.IngredientService;
import com.project.miinhareceita.tests.IngredientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class IngredientServiceTest {
    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientsRepository repository;

    private Ingredients ingredient;
    private IngredientProjection ingredientProjection;
    private List<IngredientProjection> ingredientProjectionList;

    private String ingredientName;


    @BeforeEach
    void setUp(){
        ingredientName = "Tomat";
        ingredientProjection = IngredientFactory.createIngredientProjection();
        ingredientProjectionList = new ArrayList<>();
        ingredientProjectionList.add(ingredientProjection);

        ingredient = IngredientFactory.createIngredient();

        Mockito.when(repository.searchProducts(ingredientName)).thenReturn(ingredientProjectionList);

        Mockito.when(repository.save(any())).thenReturn(ingredient);
    }

    @Test
    public void insertIngredientShouldReturnIngredientDtoWhenSucess(){
        InsertIngredientDTO dto = IngredientFactory.createIngredientDto();
        IngredientDTO result = ingredientService.insertIngredient(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), "Tomate");
    }
    @Test
    public void findByIngredientNameShouldReturnListIngredientDtoWhenSucess(){
        List<IngredientDTO> result = ingredientService.findByIngredientName(ingredientName);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), ingredientProjectionList.size());
        Assertions.assertEquals(result.getFirst().getName(), ingredientProjection.getName() );

    }

    


}
