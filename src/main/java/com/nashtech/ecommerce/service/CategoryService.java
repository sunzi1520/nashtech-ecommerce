package com.nashtech.ecommerce.service;

import com.nashtech.ecommerce.dto.CategoryDto;
import com.nashtech.ecommerce.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    public List<CategoryDto> retrieveCategories();

    public CategoryDto getCategoryById(Long categoryId);

    public CategoryDto saveCategory(CategoryDto categoryDto);

    public boolean deleteCategory(Long categoryId);

    public boolean updateCategory(CategoryDto categoryDto);

    public boolean existsById(Long categoryId);

}
