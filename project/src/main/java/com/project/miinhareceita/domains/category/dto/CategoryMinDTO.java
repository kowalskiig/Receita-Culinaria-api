package com.project.miinhareceita.domains.category.dto;

import com.project.miinhareceita.domains.category.domain.Category;
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
