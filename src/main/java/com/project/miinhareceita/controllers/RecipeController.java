package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.RecipeDTO;
import com.project.miinhareceita.dtos.RecipeMinDTO;
import com.project.miinhareceita.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<Page<RecipeMinDTO>> searchRecipeByFilters(@RequestParam(defaultValue = "") String categoriesId,
                                                                    @RequestParam(defaultValue = "") String ingredientsId,
                                                                    @RequestParam(defaultValue = "") String name,
                                                                    Pageable pageable){
        Page<RecipeMinDTO> recipePage = recipeService.searchRecipesByCategoriesIngredientsAndName(categoriesId, ingredientsId, name, pageable);
        return ResponseEntity.ok(recipePage);
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> insertRecipe (@Valid @RequestBody RecipeDTO dto){
        RecipeDTO result = recipeService.insertRecipe(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> findRecipeById(@PathVariable Long id){
        RecipeDTO result = recipeService.findRecipeById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipeById(@PathVariable Long id, @Valid @RequestBody RecipeDTO dto){
        RecipeDTO result = recipeService.updateRecipe(id, dto);
        return ResponseEntity.ok(result);
    }
}
