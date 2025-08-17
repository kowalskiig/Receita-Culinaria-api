package com.project.miinhareceita.ingredient.controller;

import com.project.miinhareceita.ingredient.dto.IngredientDTO;
import com.project.miinhareceita.ingredient.dto.InsertIngredientDTO;
import com.project.miinhareceita.ingredient.dto.UpdateIngredientDTO;
import com.project.miinhareceita.ingredient.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@Tag(name = "Ingredient", description = "Controller for ingredients")
@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService service;



    @Operation(
            description = "Save new Ingredient",
            summary = "Save new Ingredient only Admin",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422")

            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> insertIngredient(@Valid @RequestBody InsertIngredientDTO dto){
        IngredientDTO ingredientDTO = service.insertIngredient(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ingredientDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(ingredientDTO);
    }


    @Operation(
            description = "Find by Ingredient Name (Sorted), (aceppts empty values)",
            summary = "Find Ingredient by IngredientName",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "NotFound", responseCode = "400")
            }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IngredientDTO>> findByIngredientName(@RequestParam (defaultValue = "") String name) {
        List<IngredientDTO> dto = service.findByIngredientName(name);
        return ResponseEntity.ok(dto);
    }


    @Operation(
            description = "Update Ingredient",
            summary = "Update Ingredient with ingredientId only Admin ",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422")

            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{ingredientId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable Long ingredientId,@Valid @RequestBody UpdateIngredientDTO dto){
        IngredientDTO ingredientDTO = service.updateIngredient(ingredientId, dto);
        return ResponseEntity.ok(ingredientDTO);
    }

    @Operation(
            description = "DeleteById Ingredient",
            summary = "Delete Ingredient with ingredientId only Admin",
            responses = {
                    @ApiResponse(description = "NoContent", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),

            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{ingredientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long ingredientId){

        service.deleteIngredientById(ingredientId);
        return ResponseEntity.noContent().build();
    }


}

