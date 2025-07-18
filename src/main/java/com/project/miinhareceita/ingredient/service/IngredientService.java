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

    @Transactional(readOnly = false)
    public IngredientDTO updateIngredient(Long id, UpdateIngredientDTO dto) {
        try {
            Ingredients entity = repository.getReferenceById(id);
            mapDTODataToEntity(dto,entity);
            entity = repository.save(entity);
            return new IngredientDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado " + id);
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteIngredientById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void mapDTODataToEntity(ValidIngredientDTO dto, Ingredients entity) {
        if (dto.getName() != null && !dto.getName().isBlank()) {
            entity.setName(dto.getName());
        }
    }



}
