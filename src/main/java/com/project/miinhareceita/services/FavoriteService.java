package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.FavoriteDTO;
import com.project.miinhareceita.dtos.FavoriteInsertDTO;
import com.project.miinhareceita.favorite.domain.Favorite;
import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.repositories.FavoriteRepository;
import com.project.miinhareceita.repositories.RecipeRepository;
import com.project.miinhareceita.services.exceptions.DatabaseException;
import com.project.miinhareceita.services.exceptions.ForbiddenException;
import com.project.miinhareceita.services.exceptions.ResourceNotFoundException;
import com.project.miinhareceita.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository repository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private AuthService authService;

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

        Favorite favorite = RecipeFromDTOToNewEntity(dto);
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

  

    private Favorite RecipeFromDTOToNewEntity(FavoriteInsertDTO dto){
        Favorite favorite = new Favorite();
        favorite.getId().setRecipe(recipeRepository.getReferenceById(dto.getRecipeId()));
        return favorite;
    }


}
