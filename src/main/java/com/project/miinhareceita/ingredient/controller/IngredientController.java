package com.project.miinhareceita.ingredient.controller;

import com.project.miinhareceita.ingredient.dto.IngredientDTO;
import com.project.miinhareceita.ingredient.dto.InsertIngredientDTO;
import com.project.miinhareceita.ingredient.dto.UpdateIngredientDTO;
import com.project.miinhareceita.ingredient.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService service;


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<IngredientDTO> insertIngredient(@Valid @RequestBody InsertIngredientDTO dto){
        IngredientDTO ingredientDTO = service.insertIngredient(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(ingredientDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(ingredientDTO);
    }

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> findByIngredientName( @RequestParam (defaultValue = "") String name) {
        List<IngredientDTO> dto = service.findByIngredientName(name);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@PathVariable Long id,@Valid @RequestBody UpdateIngredientDTO dto){
        IngredientDTO ingredientDTO = service.updateIngredient(id, dto);
        return ResponseEntity.ok(ingredientDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id){
       service.deleteIngredientById(id);
        return ResponseEntity.noContent().build();
    }


}

