package com.project.miinhareceita.services;

import com.project.miinhareceita.category.domain.Category;
import com.project.miinhareceita.category.dto.CategoryMinDTO;
import com.project.miinhareceita.category.repository.CategoryRepository;
import com.project.miinhareceita.category.service.CategoryService;
import com.project.miinhareceita.tests.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category category;
    private List<Category> listCategory;

    @BeforeEach
    void setUp() {
        category = CategoryFactory.createCategory();
        listCategory = new ArrayList<>();

        listCategory.add(category);

        Mockito.when(categoryRepository.findAll()).thenReturn(listCategory);
    }

    @Test
    @DisplayName("Should return list of CategoryMinDTO when successful")
    public void findAllShouldReturnListCategoryMinDtoWhenSucess(){
        List<CategoryMinDTO> result = categoryService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.size(), 1);
        Assertions.assertEquals(result.getFirst().getName(), category.getName());
        Assertions.assertEquals(result.getFirst().getId(), category.getId());

        Mockito.verify(categoryRepository, Mockito.times(1)).findAll();

    }
}

