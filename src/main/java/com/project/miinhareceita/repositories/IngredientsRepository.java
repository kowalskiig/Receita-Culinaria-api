package com.project.miinhareceita.repositories;

import com.project.miinhareceita.entities.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
}
