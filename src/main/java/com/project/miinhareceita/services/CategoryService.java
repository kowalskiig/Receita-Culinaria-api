package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.CategoryMinDTO;
import com.project.miinhareceita.entities.Category;
import com.project.miinhareceita.repositories.CategoryRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryMinDTO> findAll() {
        List<Category> result = repository.findAll();
        return result.stream().map(x -> new CategoryMinDTO(x)).toList();
    }
}