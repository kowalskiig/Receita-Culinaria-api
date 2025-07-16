package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.FavoriteDTO;
import com.project.miinhareceita.dtos.FavoriteInsertDTO;
import com.project.miinhareceita.dtos.RecipeDTO;
import com.project.miinhareceita.services.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<List<FavoriteDTO>> getFavoriteRecipesByUser(){
        List<FavoriteDTO> result = service.getFavoriteRecipesByUser();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping
    public ResponseEntity<FavoriteDTO> insertFavorite(@Valid @RequestBody FavoriteInsertDTO dto) {
        FavoriteDTO result = service.insertFavoriteRecipe(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long recipeId){
        service.deleteFavoriteByRecipeId(recipeId);
        return ResponseEntity.noContent().build();
    }

    }


