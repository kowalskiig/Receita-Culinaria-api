package com.project.miinhareceita.domains.category.controller;

import com.project.miinhareceita.domains.category.dto.CategoryMinDTO;
import com.project.miinhareceita.domains.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Category", description = "Controller for categories")
    @RestController
    @RequestMapping(value = "/categories")
    public class CategoryController {

        @Autowired
        private CategoryService service;

    @Operation(
            description = "Get Categories",
            summary = "List All Categories",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
            }
    )
        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<CategoryMinDTO>> findAll() {
            List<CategoryMinDTO> dto = service.findAll();
            return ResponseEntity.ok(dto);
        }
}

