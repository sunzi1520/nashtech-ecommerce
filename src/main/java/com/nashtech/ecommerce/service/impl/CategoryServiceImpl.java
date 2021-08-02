package com.nashtech.ecommerce.service.impl;

import com.nashtech.ecommerce.dto.CategoryDto;
import com.nashtech.ecommerce.entity.Category;
import com.nashtech.ecommerce.entity.Product;
import com.nashtech.ecommerce.repository.CategoryRepository;
import com.nashtech.ecommerce.repository.ProductCategoriesRepository;
import com.nashtech.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductCategoriesRepository productCategoriesRepository;

    private CategoryDto convertCategoryToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    @Override
    public List<CategoryDto> retrieveCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> convertCategoryToCategoryDto(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);

        if (!existingCategory.isPresent()) {
            return null;
        }

        return convertCategoryToCategoryDto(existingCategory.get());

    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category category = convertCategoryDtoToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);

        CategoryDto savedCategoryDto = convertCategoryToCategoryDto(savedCategory);
        return savedCategoryDto;
    }

    @Override
    @Transactional
    public boolean deleteCategory(Long categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);

        if (existingCategory.isPresent()) {
            productCategoriesRepository.deleteByCategoryId(categoryId);
            categoryRepository.deleteById(categoryId);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateCategory(CategoryDto categoryDto) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryDto.getId());

        if (existingCategory.isPresent()) {
            Category oldCategory = existingCategory.get();
            oldCategory.setName(categoryDto.getName());
            oldCategory.setDescription(categoryDto.getDescription());
            categoryRepository.save(oldCategory);

            return true;
        }

        return false;
    }

    @Override
    public boolean existsById(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
