package com.project.miinhareceita.domains.favorite.service;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.domains.favorite.domain.Favorite;
import com.project.miinhareceita.domains.favorite.dto.FavoriteDTO;
import com.project.miinhareceita.domains.favorite.repository.FavoriteRepository;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.shared.exceptions.ConflictException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.domains.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository repository;
    private final RecipeRepository recipeRepository;
    private final AuthService authService;

    public FavoriteService(
            FavoriteRepository repository,
            RecipeRepository recipeRepository,
            AuthService authService
    ) {
        this.repository = repository;
        this.recipeRepository = recipeRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public List<FavoriteDTO> getFavoriteRecipesMe() {
        User user = authService.authenticated();
        List<Favorite> result = repository.findFavoritesByUserId(user.getId());

        return result.stream().map(FavoriteDTO::new).toList();
    }

    @Transactional
    public FavoriteDTO insertFavoriteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe does not exist"));

        User user = authService.authenticated();

        if (repository.existsByUserIdAndRecipeId(user.getId(), recipeId)) {
            throw new ConflictException("Recipe is already favorited");
        }

        return new FavoriteDTO(repository.save(new Favorite(recipe, user)));
    }

    @Transactional
    public void deleteFavoriteByRecipeId(Long recipeId) {

        User user = authService.authenticated();

        if(!verificationExistsRecipeFavoriteInUserId(user.getId(), recipeId)){
            throw new ResourceNotFoundException("Recipe does not appear in your list");
        }
            repository.deleteByRecipeIdAndUser(recipeId, user.getId());
    }


    private boolean verificationExistsRecipeFavoriteInUserId(Long userId, Long recipeId){
        return repository.existsByUserIdAndRecipeId(userId, recipeId);
    }
}
