package com.project.miinhareceita.controllers;

import com.project.miinhareceita.dtos.ReviewDTO;
import com.project.miinhareceita.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

