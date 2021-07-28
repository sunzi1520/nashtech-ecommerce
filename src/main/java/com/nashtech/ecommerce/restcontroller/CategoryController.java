package com.nashtech.ecommerce.restcontroller;

import com.nashtech.ecommerce.dto.CategoryDto;
import com.nashtech.ecommerce.dto.MessageResponse;
import com.nashtech.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.retrieveCategories();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getById(@PathVariable Long categoryId) {
        CategoryDto categoryDto = categoryService.getCategoryById(categoryId);

        if (categoryDto != null) {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("Categories not found!"), HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId,
                                                 @RequestBody CategoryDto categoryDto) {

        if (!categoryService.existsById(categoryId)) {
            return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
        }

        if ((categoryDto.getId() == null) || (categoryId != categoryDto.getId())) {
            categoryDto.setId(categoryId);
        }

        if (categoryService.updateCategory(categoryDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
       if (categoryService.deleteCategory(categoryId)) {
           return new ResponseEntity<>(HttpStatus.OK);
       }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
