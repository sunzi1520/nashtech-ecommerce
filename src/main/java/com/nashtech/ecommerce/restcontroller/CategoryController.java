package com.nashtech.ecommerce.restcontroller;

import com.nashtech.ecommerce.entity.Category;
import com.nashtech.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.retrieveCategories();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getById(@PathVariable Long categoryId) {
        Category category = categoryService.getCategoryById(categoryId);

        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Category saveCategory(@RequestParam Category category) {
        return categoryService.saveCategory(category);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Boolean> updateCategory(@PathVariable Long categoryId,
                                                 @RequestBody Category category) {
        if (categoryService.updateCategory(category)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long categoryId) {
       if (categoryService.deleteCategory(categoryId)) {
           return new ResponseEntity<>(HttpStatus.OK);
       }

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
