package com.nashtech.ecommerce.restcontroller;

import com.nashtech.ecommerce.dto.MessageResponse;
import com.nashtech.ecommerce.dto.ProductDto;
import com.nashtech.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.retrieveProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId) {
        ProductDto productDto = productService.getProductById(productId);

        if (productDto != null) {
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("Product not found!"), HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductDto productDto) {
        try {
            return new ResponseEntity<>(productService.saveProduct(productDto), HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        if (productService.deleteProduct(productId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                                           @RequestBody ProductDto productDto) {
        if (!productService.existsById(productId)) {
            return new ResponseEntity<>(new MessageResponse("Product not found"), HttpStatus.NOT_FOUND);
        }

        if (productDto.getId() == null || !productId.equals(productDto.getId())) {
            productDto.setId(productId);
        }

        try {

            if (productService.updateProduct(productDto)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>("Unknown error", HttpStatus.NOT_MODIFIED);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
