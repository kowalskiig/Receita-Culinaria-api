package com.project.miinhareceita.favorite.controller;

import com.project.miinhareceita.favorite.dto.FavoriteDTO;
import com.project.miinhareceita.favorite.dto.FavoriteInsertDTO;
import com.project.miinhareceita.favorite.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Operation(
            description = "Get favorite recipes for the authenticated user",
            summary = "Retrieve all favorite recipes for the current user",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/me")
    public ResponseEntity<List<FavoriteDTO>> getFavoriteRecipesMe(){
        List<FavoriteDTO> result = service.getFavoriteRecipesMe();
        return ResponseEntity.ok(result);
    }

    @Operation(
            description = "Save new Favorite",
            summary = "Save new FavoriteRecipe by recipeId in User Authenticated List",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Conflict", responseCode = "409")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriteDTO> insertFavorite(@PathVariable Long recipeId) {
        FavoriteDTO result = service.insertFavoriteRecipe(recipeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(
            description = "Delete a favorite recipe",
            summary = "Delete a favorite recipe by recipeId for the current user",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long recipeId){
        service.deleteFavoriteByRecipeId(recipeId);
        return ResponseEntity.noContent().build();
    }

    }


