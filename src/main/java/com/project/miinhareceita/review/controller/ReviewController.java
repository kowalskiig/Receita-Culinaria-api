package com.project.miinhareceita.review.controller;

import com.project.miinhareceita.review.dto.InsertReviewDTO;
import com.project.miinhareceita.review.dto.ReviewDTO;
import com.project.miinhareceita.review.dto.UpdateReviewDTO;
import com.project.miinhareceita.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;


    @Operation(
            description = "Find Reviews By recipeId",
            summary = "Find all reviews by recipeId",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
            }
    )
    @GetMapping(value = "/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ReviewDTO>> findReviewByRecipeId(@PathVariable Long id, Pageable pageable){
        Page<ReviewDTO> result = service.findReviewByRecipeId(id,pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(
            description = "Save new review",
            summary = "Save new Review by recipeId with User Authenticated",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> insertReview(@PathVariable Long id, @Valid @RequestBody InsertReviewDTO dto){
        ReviewDTO result = service.insertReview( id,dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);

    }

    @Operation(
            description = "Delete review",
            summary = "Delete review with reviewId of User Authenticated",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReview(@PathVariable Long id){
         service.deleteReview(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            description = "Update review",
            summary = "Update Review by reviewId with User Authenticated",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id, @RequestBody UpdateReviewDTO dto){
        ReviewDTO result = service.updateReview(id, dto);
        return ResponseEntity.ok(result);
    }
}

