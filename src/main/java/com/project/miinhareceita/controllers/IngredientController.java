package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.IngredientDTO;
import com.project.miinhareceita.services.IngredientService;
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

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<IngredientDTO> insertUser( @Valid @RequestBody IngredientDTO dto){
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
    public ResponseEntity<IngredientDTO> updateUser(@PathVariable Long id,@Valid @RequestBody IngredientDTO dto){
        IngredientDTO ingredientDTO = service.updateIngredient(id, dto);
        return ResponseEntity.ok(ingredientDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
       service.deleteIngredientById(id);
        return ResponseEntity.noContent().build();
    }
}

