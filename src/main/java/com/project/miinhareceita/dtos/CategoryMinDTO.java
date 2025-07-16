package com.project.miinhareceita.dtos;

import com.project.miinhareceita.entities.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class CategoryMinDTO {
    private Long id;
    private String name;

    public CategoryMinDTO(Category category){
        id = category.getId();
        name = category.getName();
    }
}
