package com.nashtech.ecommerce.service;

import com.nashtech.ecommerce.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    public List<Category> retrieveCategories();

    public Category getCategoryById(Long categoryId);

    public Category saveCategory(Category category);

    public boolean deleteCategory(Long categoryId);

    public boolean updateCategory(Category category);

}
