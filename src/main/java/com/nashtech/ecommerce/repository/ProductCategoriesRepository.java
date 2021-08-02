package com.nashtech.ecommerce.repository;

import com.nashtech.ecommerce.entity.ProductCategories;
import com.nashtech.ecommerce.entity.ProductCategoriesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoriesRepository extends JpaRepository<ProductCategories, ProductCategoriesId> {

    void deleteByCategoryId(Long categoryId);

}
