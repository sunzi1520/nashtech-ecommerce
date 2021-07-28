package com.nashtech.ecommerce.service;

import com.nashtech.ecommerce.dto.ProductDto;
import com.nashtech.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    public List<ProductDto> retrieveProducts();

    public ProductDto getProductById(Long productId);

    public ProductDto saveProduct(ProductDto productDto);

    public boolean deleteProduct(Long productId);

    public boolean updateProduct(ProductDto productDto);

    public boolean existsById(Long productId);
}
