package com.nashtech.ecommerce.service.impl;

import com.nashtech.ecommerce.entity.Category;
import com.nashtech.ecommerce.repository.CategoryRepository;
import com.nashtech.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> retrieveCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);

        if (!existingCategory.isPresent()) {
            return existingCategory.get();
        } else {
            return null;
        }

    }

    @Override
    public Category saveCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getId());

        if (!existingCategory.isPresent()) {
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCategory(Long categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);

        if (existingCategory.isPresent()) {
            categoryRepository.delete(existingCategory.get());
            return true;
        }

        return false;
    }

    @Override
    public boolean updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getId());

        if (existingCategory.isPresent()) {
            Category oldCategory = existingCategory.get();
            oldCategory.setName(category.getName());
            oldCategory.setDescription(category.getDescription());

            return true;
        }

        return false;
    }
}
