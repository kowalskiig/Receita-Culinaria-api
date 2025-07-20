package com.project.miinhareceita.services;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.favorite.domain.Favorite;
import com.project.miinhareceita.favorite.dto.FavoriteDTO;
import com.project.miinhareceita.favorite.repository.FavoriteRepository;
import com.project.miinhareceita.favorite.service.FavoriteService;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.tests.FavoriteFactory;
import com.project.miinhareceita.tests.RecipeFactory;
import com.project.miinhareceita.tests.UserFactory;
import com.project.miinhareceita.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class FavoriteServiceTest {
    @InjectMocks
    private FavoriteService favoriteService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private AuthService authService;


    private Long nonExistingRecipeId, existingRecipeId, existingUserId, nonExistingUserId;

    private Recipe recipe;
    private User user;
    private Favorite favorite;

    @BeforeEach
    void setUp(){
        existingRecipeId = 1L;
        nonExistingRecipeId = 2L;

        existingUserId =1L;
        nonExistingUserId =2L;

        recipe = RecipeFactory.createRecipe();
        user = UserFactory.createUser();

        favorite = FavoriteFactory.createFavorite(recipe, user);

        Mockito.when(authService.authenticated()).thenReturn(user);

        Mockito.when(recipeRepository.findById(existingRecipeId)).thenReturn(Optional.of(recipe));
        Mockito.when(recipeRepository.findById(nonExistingRecipeId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(favoriteRepository.existsByUserIdAndRecipeId(existingUserId, existingRecipeId))
                .thenReturn(false);

        Mockito.when(recipeRepository.getReferenceById(existingRecipeId)).thenReturn(recipe);

        Mockito.when(favoriteRepository.save(any())).thenReturn(favorite);



    }
    @Test
    public void insertFavoriteRecipeShouldReturnFavoriteDtoWhenSucess(){
        FavoriteDTO result = favoriteService.insertFavoriteRecipe(existingRecipeId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getRecipeFavoriteDTO().getId(), recipe.getId());
        Assertions.assertEquals(result.getRecipeFavoriteDTO().getTitle(), recipe.getTitle());


        Mockito.verify(recipeRepository, Mockito.times(1)).findById(existingRecipeId);
        Mockito.verify(favoriteRepository, Mockito.times(1)).existsByUserIdAndRecipeId(existingUserId, existingRecipeId);
        Mockito.verify(favoriteRepository, Mockito.times(1)).save(any());
        Mockito.verify(authService, Mockito.times(1)).authenticated();
    }

}
