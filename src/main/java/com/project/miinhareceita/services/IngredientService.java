package com.project.miinhareceita.services;

import com.project.miinhareceita.ingredient.domain.Ingredients;
import com.project.miinhareceita.ingredient.dto.IngredientDTO;
import com.project.miinhareceita.ingredient.projection.IngredientProjection;
import com.project.miinhareceita.ingredient.repository.IngredientsRepository;
import com.project.miinhareceita.services.exceptions.DatabaseException;
import com.project.miinhareceita.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
        Ingredients entity = new Ingredients();
        copyDTODataToEntity(dto, entity);

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
    public IngredientDTO updateIngredient(Long id,IngredientDTO dto) {
        try {
            Ingredients entity = repository.getReferenceById(id);
            copyDTODataToEntity(dto,entity);
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

    private void copyDTODataToEntity(IngredientDTO dto, Ingredients entity){
        entity.setName(dto.getName());
    }



}
