package com.project.miinhareceita.ingredient.service;

import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.ingredient.dto.IngredientDTO;
import com.project.miinhareceita.ingredient.dto.InsertIngredientDTO;
import com.project.miinhareceita.ingredient.dto.UpdateIngredientDTO;
import com.project.miinhareceita.ingredient.dto.ValidIngredientDTO;
import com.project.miinhareceita.ingredient.projection.IngredientProjection;
import com.project.miinhareceita.ingredient.repository.IngredientsRepository;
import com.project.miinhareceita.shared.exceptions.DatabaseException;
import com.project.miinhareceita.shared.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final IngredientsRepository repository;

    public IngredientService(IngredientsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public IngredientDTO insertIngredient(InsertIngredientDTO dto){
        Ingredients entity = new Ingredients();
        mapDTODataToEntity(dto, entity);

        return  new IngredientDTO(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<IngredientDTO> findByIngredientName(String name){
        List<IngredientProjection> listProjection = repository.searchIngredientsByName(name);

        return listProjection.stream()
                .map(projection -> new IngredientDTO(projection.getId(), projection.getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public IngredientDTO updateIngredient(Long id, UpdateIngredientDTO dto) {
        Ingredients entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient does not exist " + id));

        mapDTODataToEntity(dto,entity);

        return new IngredientDTO(repository.save(entity));
        }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteIngredientById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ingredient does not exist" + id);
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity failure");
        }
    }

    private void mapDTODataToEntity(ValidIngredientDTO dto, Ingredients entity) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            entity.setName(dto.getName());
        }
    }



}
