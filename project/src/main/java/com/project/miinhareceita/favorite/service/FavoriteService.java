package com.project.miinhareceita.favorite.service;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.favorite.domain.Favorite;
import com.project.miinhareceita.favorite.dto.FavoriteDTO;
import com.project.miinhareceita.favorite.repository.FavoriteRepository;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
import com.project.miinhareceita.shared.exceptions.ConflictException;
import com.project.miinhareceita.shared.exceptions.DatabaseException;
import com.project.miinhareceita.shared.exceptions.ForbiddenException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.user.domain.User;
import org.springframework.dao.DataIntegrityViolationException;
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

        User user = authService.authenticated();

        verificationExistsRecipeId(recipeId);
        verificationExistsRecipeFavoriteInUserId(user.getId(), recipeId);

        Favorite favorite = new Favorite();

        Recipe recipe = recipeRepository.getReferenceById(recipeId);

        favorite.getId().setRecipe(recipe);
        favorite.getId().setUser(user);

        favorite = repository.save(favorite);

        return new FavoriteDTO(favorite);
    }

    @Transactional
    public void deleteFavoriteByRecipeId(Long recipeId) {
        User user = authService.authenticated();

        verificationExistsRecipeId(recipeId);

        if (!repository.existsByUserIdAndRecipeId(user.getId(), recipeId)) {
            throw new ResourceNotFoundException("Não existe essa receita nas suas favoritas");
        }

        try {
            repository.deleteByRecipeIdAndUser(recipeId, user.getId());
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void verificationExistsRecipeId(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não existe"));

    }


    private void verificationExistsRecipeFavoriteInUserId(Long userId, Long recipeId){
        if (repository.existsByUserIdAndRecipeId(userId, recipeId)) {
            throw new ConflictException("Receita já está favoritada");
        }
    }
}
