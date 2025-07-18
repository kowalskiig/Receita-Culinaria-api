package com.project.miinhareceita.favorite.service;

import com.project.miinhareceita.auth.service.AuthService;
import com.project.miinhareceita.favorite.domain.Favorite;
import com.project.miinhareceita.favorite.dto.FavoriteDTO;
import com.project.miinhareceita.favorite.dto.FavoriteInsertDTO;
import com.project.miinhareceita.favorite.repository.FavoriteRepository;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.recipe.repository.RecipeRepository;
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
    public List<FavoriteDTO> getFavoriteRecipesByUser() {
        User user = authService.authenticated();
        List<Favorite> result = repository.findFavoritesByUserId(user.getId());
        return result.stream().map(FavoriteDTO::new).toList();
    }

    @Transactional
    public FavoriteDTO insertFavoriteRecipe(FavoriteInsertDTO dto) {
        User user = authService.authenticated();
        List<Favorite> result = repository.findFavoritesByUserId(user.getId());

        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Receita não existe"));

        if (repository.existsByUserIdAndRecipeId(user.getId(), dto.getRecipeId())) {
            throw new DatabaseException("Receita já está favoritada");
        }

        Favorite favorite = mapToFavoriteEntity(dto);
        favorite.getId().setUser(user);

        favorite = repository.save(favorite);

        return new FavoriteDTO(favorite);
    }

    @Transactional
    public void deleteFavoriteByRecipeId(Long recipeId) {
        User user = authService.authenticated();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Receita não existe"));

        if (!repository.existsByUserIdAndRecipeId(user.getId(), recipeId)) {
            throw new ForbiddenException("Não tem permissão para isso");
        }
        try {
            repository.deleteByRecipeIdAndUser(recipeId, user.getId());
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

  

    private Favorite mapToFavoriteEntity(FavoriteInsertDTO dto){
        Favorite favorite = new Favorite();
        favorite.getId().setRecipe(recipeRepository.getReferenceById(dto.getRecipeId()));
        return favorite;
    }


}
