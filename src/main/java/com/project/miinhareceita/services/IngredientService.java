package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.IngredientDTO;
import com.project.miinhareceita.entities.Ingredients;
import com.project.miinhareceita.projections.IngredientProjection;
import com.project.miinhareceita.repositories.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    @Autowired
    private IngredientsRepository repository;

    @Transactional
    public IngredientDTO insertIngredient(IngredientDTO dto){
        Ingredients entity = copyDTODataToEntity(dto);
        entity = repository.save(entity);
        return  new IngredientDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<IngredientDTO> findByIngredientName(String name){
        List<IngredientProjection> listProjection = repository.searchProducts(name);

        return listProjection.stream()
                .map(projection -> new IngredientDTO(projection.getId(), projection.getName()))
                .sorted(Comparator.comparing(IngredientDTO::getName))
                .collect(Collectors.toList());
    }



    private Ingredients copyDTODataToEntity(IngredientDTO dto){
        Ingredients ingredients = new Ingredients();
        ingredients.setId(dto.getId());
        ingredients.setName(dto.getName());
        return ingredients;
    }
}
