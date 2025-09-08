package com.project.miinhareceita.recipe.controller;

import com.project.miinhareceita.recipe.dto.InsertRecipeDTO;
import com.project.miinhareceita.recipe.dto.RecipeDTO;
import com.project.miinhareceita.recipe.dto.RecipeMinDTO;
import com.project.miinhareceita.recipe.dto.UpdateRecipeDTO;
import com.project.miinhareceita.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Recipe", description = "Controller for recipes")
@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Operation(
            description = "Get paged recipes",
            summary = "Page recipes with filters, categoriesId, ingredientsId or name (all can be null)",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RecipeMinDTO>> searchRecipeByFilters(@RequestParam(defaultValue = "") String categoriesId,
                                                                    @RequestParam(defaultValue = "") String ingredientsId,
                                                                    @RequestParam(defaultValue = "") String name,
                                                                    Pageable pageable){

        Page<RecipeMinDTO> recipePage = recipeService.searchRecipesByCategoriesIngredientsAndName(categoriesId, ingredientsId, name, pageable);
        return ResponseEntity.ok(recipePage);
    }

    @Operation(
            description = "Save new recipe",
            summary = "Save new Recipe with User Authenticated",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> insertRecipe(@Valid @RequestBody InsertRecipeDTO dto){
        RecipeDTO result = recipeService.insertRecipe(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);

    }

    @Operation(
            description = "FindById",
            summary = "Find Recipe By recipeId",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "NotFound", responseCode = "404"),
            }
    )
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> findRecipeById(@PathVariable Long id){
        RecipeDTO result = recipeService.findRecipeById(id);
        return ResponseEntity.ok(result);
    }

    @Operation(
            description = "Update recipe",
            summary = "Update Recipe with User Authenticated and recipeId",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> updateRecipeById(@PathVariable Long id, @Valid @RequestBody UpdateRecipeDTO dto){
        RecipeDTO result = recipeService.updateRecipe(id, dto);
        return ResponseEntity.ok(result);
    }
}
