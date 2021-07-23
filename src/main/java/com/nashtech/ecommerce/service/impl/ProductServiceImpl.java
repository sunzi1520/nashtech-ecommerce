package com.nashtech.ecommerce.service.impl;

import com.nashtech.ecommerce.entity.Product;
import com.nashtech.ecommerce.repository.ProductRepository;
import com.nashtech.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> retrieveProducts() {
        return null;
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return Optional.empty();
    }

    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public void updateProduct(Product product) {

    }
}
