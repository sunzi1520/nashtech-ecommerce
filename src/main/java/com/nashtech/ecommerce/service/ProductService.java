package com.nashtech.ecommerce.service;

import com.nashtech.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    public List<Product> retrieveProducts();

    public Optional<Product> getProductById(Long productId);

    public Product saveProduct(Product product);

    public void deleteProduct(Long productId);

    public void updateProduct(Product product);

}
