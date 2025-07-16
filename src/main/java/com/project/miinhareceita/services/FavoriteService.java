package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.FavoriteDTO;
import com.project.miinhareceita.dtos.FavoriteInsertDTO;
import com.project.miinhareceita.dtos.UserDTO;
import com.project.miinhareceita.entities.Favorite;
import com.project.miinhareceita.entities.Recipe;
import com.project.miinhareceita.entities.User;
import com.project.miinhareceita.repositories.FavoriteRepository;
import com.project.miinhareceita.repositories.RecipeRepository;
import com.project.miinhareceita.services.exceptions.DatabaseException;
import com.project.miinhareceita.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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

    private Favorite RecipeFromDTOToNewEntity(FavoriteInsertDTO dto){
        Favorite favorite = new Favorite();
        favorite.getId().setRecipe(recipeRepository.getReferenceById(dto.getRecipeId()));
        return favorite;
    }
}
