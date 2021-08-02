package com.nashtech.ecommerce.repository;

import com.nashtech.ecommerce.entity.Product;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByCategories_Id(Long categoryId);

    boolean existsById(Long productId);

}
