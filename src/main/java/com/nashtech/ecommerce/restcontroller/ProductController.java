package com.nashtech.ecommerce.restcontroller;

import com.nashtech.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

}
