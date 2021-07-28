package com.nashtech.ecommerce.service.impl;

import com.nashtech.ecommerce.dto.CategoryDto;
import com.nashtech.ecommerce.dto.ProductDto;
import com.nashtech.ecommerce.entity.Category;
import com.nashtech.ecommerce.entity.Product;
import com.nashtech.ecommerce.repository.CategoryRepository;
import com.nashtech.ecommerce.repository.ProductRepository;
import com.nashtech.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private ProductDto convertProductToProductDto(Product product) {
        return modelMapper.typeMap(Product.class, ProductDto.class)
                .map(product);
    }

    private Product convertProductDtoToProduct(ProductDto productDto) {
        List<CategoryDto> filledCategories = productDto.getCategories().stream()
                                        .map(categoryDto -> {
                                            Category category = categoryRepository.findById(categoryDto.getId())
                                                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                                            return modelMapper.map(category, CategoryDto.class);
                                        })
                                        .collect(Collectors.toList());
        productDto.setCategories(filledCategories);
        return modelMapper.typeMap(ProductDto.class, Product.class)
                .map(productDto);
    }

    @Override
    public List<ProductDto> retrieveProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(product -> convertProductToProductDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            return convertProductToProductDto(product.get());
        }

        return null;
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        try {
            Product product = convertProductDtoToProduct(productDto);
            Product savedProduct = productRepository.save(product);
            ProductDto savedProductDto = convertProductToProductDto(savedProduct);
            return savedProductDto;
        }
        catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            productRepository.deleteById(productId);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateProduct(ProductDto productDto) {
        return false;
    }

    @Override
    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }
}
