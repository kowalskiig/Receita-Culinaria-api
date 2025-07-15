package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.ReviewDTO;
import com.project.miinhareceita.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @GetMapping("/{id}")
    public ResponseEntity<Page<ReviewDTO>> findReviewByRecipeId(@PathVariable Long id, Pageable pageable){
        Page<ReviewDTO> result = service.findReviewByRecipeId(id,pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ReviewDTO> insertRecipe (@PathVariable Long id,@Valid @RequestBody ReviewDTO dto){
        ReviewDTO result = service.insertReview( id,dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> findReviewByRecipeId(@PathVariable Long id){
         service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody ReviewDTO dto){
        ReviewDTO result = service.updateReview(id, dto);
        return ResponseEntity.ok(result);
    }
}

