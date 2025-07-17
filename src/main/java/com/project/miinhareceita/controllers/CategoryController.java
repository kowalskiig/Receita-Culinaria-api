package com.project.miinhareceita.controllers;

import com.project.miinhareceita.category.dto.CategoryMinDTO;
import com.project.miinhareceita.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequestMapping(value = "/categories")
    public class CategoryController {

        @Autowired
        private CategoryService service;

        @GetMapping
        public ResponseEntity<List<CategoryMinDTO>> findAll() {
            List<CategoryMinDTO> dto = service.findAll();
            return ResponseEntity.ok(dto);
        }
}

