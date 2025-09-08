package com.project.miinhareceita.domains.review.controller;

import com.project.miinhareceita.domains.review.dto.InsertReviewDTO;
import com.project.miinhareceita.domains.review.dto.ReviewDTO;
import com.project.miinhareceita.domains.review.dto.UpdateReviewDTO;
import com.project.miinhareceita.domains.review.service.ReviewService;
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

@Tag(name = "Review", description = "Controller for reviews")
@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;


    @Operation(
            description = "Get Page Reviews of a Recipe",
            summary = "Get a page with reviews of a Recipe passing a recipeId",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
            }
    )
    @GetMapping(value = "/{recipeId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ReviewDTO>> findReviewByRecipeId(@PathVariable Long recipeId, Pageable pageable){
        Page<ReviewDTO> result = service.findReviewByRecipeId(recipeId,pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(
            description = "Save new review",
            summary = "Save new Review passing a recipeId, with User Authenticated",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(value = "/{recipeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> insertReview(@PathVariable Long recipeId, @Valid @RequestBody InsertReviewDTO dto){
        ReviewDTO result = service.insertReview( recipeId,dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);

    }

    @Operation(
            description = "Delete review",
            summary = "Delete review passing reviewId of User Authenticated",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500")
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping(value = "/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId){
         service.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            description = "Update review",
            summary = "Update Review passing reviewId with User Authenticated",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Unprocessable Entity", responseCode = "422"),
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(value = "/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long reviewId, @RequestBody UpdateReviewDTO dto){
        ReviewDTO result = service.updateReview(reviewId, dto);
        return ResponseEntity.ok(result);
    }
}

