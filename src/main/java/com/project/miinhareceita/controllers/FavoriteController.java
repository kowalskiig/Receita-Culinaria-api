package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.FavoriteDTO;
import com.project.miinhareceita.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

