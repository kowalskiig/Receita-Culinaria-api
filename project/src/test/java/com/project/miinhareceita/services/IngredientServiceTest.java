package com.project.miinhareceita.services;

import com.project.miinhareceita.domains.ingredient.domain.Ingredients;
import com.project.miinhareceita.domains.ingredient.dto.IngredientDTO;
import com.project.miinhareceita.domains.ingredient.dto.InsertIngredientDTO;
import com.project.miinhareceita.domains.ingredient.dto.UpdateIngredientDTO;
import com.project.miinhareceita.domains.ingredient.projection.IngredientProjection;
import com.project.miinhareceita.domains.ingredient.repository.IngredientsRepository;
import com.project.miinhareceita.domains.ingredient.service.IngredientService;
import com.project.miinhareceita.shared.exceptions.DatabaseException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.tests.IngredientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class IngredientServiceTest {
    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientsRepository repository;

    private Ingredients ingredient;
    private IngredientProjection ingredientProjection;
    private List<IngredientProjection> ingredientProjectionList;
    private Long existingId, nonExistingId, existingIdIntegrityViolated;

    private String ingredientName;


    @BeforeEach
    void setUp(){
        existingId = 1L;
        nonExistingId = 2L;
        existingIdIntegrityViolated =3L;

        ingredientName = "Tomat";
        ingredientProjection = IngredientFactory.createIngredientProjection();
        ingredientProjectionList = new ArrayList<>();
        ingredientProjectionList.add(ingredientProjection);

        ingredient = IngredientFactory.createIngredient();

        Mockito.when(repository.searchIngredientsByName(ingredientName)).thenReturn(ingredientProjectionList);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(ingredient));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.save(any())).thenReturn(ingredient);

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(existingIdIntegrityViolated)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(existingIdIntegrityViolated);
    }

    @Test
    public void insertIngredientShouldReturnIngredientDtoWhenSucess(){
        InsertIngredientDTO dto = IngredientFactory.createIngredientDto();
        IngredientDTO result = ingredientService.insertIngredient(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), "Tomate");
        verify(repository, times(1)).save(any(Ingredients.class));
    }
    @Test
    public void findByIngredientNameShouldReturnListIngredientDtoWhenSucess(){
        List<IngredientDTO> result = ingredientService.findByIngredientName(ingredientName);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), ingredientProjectionList.size());
        Assertions.assertEquals(result.getFirst().getName(), ingredientProjection.getName() );
        verify(repository, times(1)).searchIngredientsByName(ingredientName);

    }

    @Test
    public void updateIngredientShouldReturnIngredientDtoUpdatedWhenSucess(){

        UpdateIngredientDTO dto = IngredientFactory.createUpdateIngredientDTO();
        Ingredients ingredients = IngredientFactory.createIngredient(existingId, dto.getName());

        Mockito.when(repository.save(any())).thenReturn(ingredients);

        IngredientDTO result = ingredientService.updateIngredient(existingId, dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), ingredients.getId());
        Assertions.assertEquals(result.getName(), ingredients.getName());

        verify(repository, times(1)).findById(eq(existingId));
        verify(repository, times(1)).save(
                Mockito.argThat(entity -> entity.getName().equals(dto.getName()) && entity.getId().equals(existingId))
        );
    }

    @Test
    public void updateIngredientShouldThrowNewResourceNotFoundExceptionWhenIdDoesNotExists(){
        UpdateIngredientDTO dto = IngredientFactory.createUpdateIngredientDTO();
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.updateIngredient(nonExistingId, dto);
        });
        verify(repository, times(0)).save(any(Ingredients.class));

    }
    @Test
    public void deleteIngredientByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.deleteIngredientById(nonExistingId);
        });
        verify(repository, times(1)).existsById(nonExistingId);
        verify(repository, times(0)).deleteById(nonExistingId);
    }

    @Test
    public void deleteIngredientByIdShoudlReturnNothingWhenSucess(){
        Assertions.assertDoesNotThrow(() -> {
            ingredientService.deleteIngredientById(existingId);
        });
        verify(repository, times(1)).existsById(existingId);
        verify(repository, times(1)).deleteById(existingId);
    }

    @Test
    public void deleteIngredientByIdShouldReturnDataBaseExceptionWhenDataIntegrityViolated(){
        Assertions.assertThrows(DatabaseException.class, () -> {
            ingredientService.deleteIngredientById(existingIdIntegrityViolated);
        });
        verify(repository, times(1)).existsById(existingIdIntegrityViolated);
        verify(repository, times(1)).deleteById(existingIdIntegrityViolated);
    }


}
