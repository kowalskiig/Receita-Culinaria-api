package com.project.miinhareceita.favorite.controller;

import com.project.miinhareceita.favorite.dto.FavoriteDTO;
import com.project.miinhareceita.favorite.dto.FavoriteInsertDTO;
import com.project.miinhareceita.favorite.service.FavoriteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Favorite", description = "Controller for favorites")
@RestController
@RequestMapping(value = "/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<List<FavoriteDTO>> getFavoriteRecipesMe(){
        List<FavoriteDTO> result = service.getFavoriteRecipesMe();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/{recipeId}")
    public ResponseEntity<FavoriteDTO> insertFavorite(@PathVariable Long recipeId) {
        FavoriteDTO result = service.insertFavoriteRecipe(recipeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long recipeId){
        service.deleteFavoriteByRecipeId(recipeId);
        return ResponseEntity.noContent().build();
    }

    }


