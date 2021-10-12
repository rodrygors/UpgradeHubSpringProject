package com.example.loja.controllers;

import com.example.loja.models.Product;
import com.example.loja.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class ProductController {
    @Autowired
    ProductService productServ;

    //Get all products
    @GetMapping("/getProducts")
    public List<Product> getProducts() {
        return productServ.findAll();
    }

    //Get product by id
    @GetMapping("/getProductById/{id}")
    public Optional<Product> getProductById(@PathVariable(value = "id") Long id) {
        return productServ.findById(id);
    }

    //Create product
    @PostMapping(value = "/createProduct", consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody Product product) {
        Product newProduct = Product.builder().description(product.getDescription()).price(product.getPrice()).build();
        productServ.save(newProduct);
        return newProduct;
    }

    //Update product
    @PutMapping(value = "updateProducts/{id}", consumes = "application/json", produces = "application/json")
    public Product updateProduct(@PathVariable(value = "id") Long id, @RequestBody Product product) {
        System.out.println(id);
        Optional<Product> productToBeUpdated = productServ.findById(id);
        if (productToBeUpdated.isPresent()) {
            productToBeUpdated.get().setDescription(product.getDescription());
            productToBeUpdated.get().setPrice(product.getPrice());
            productServ.save(productToBeUpdated.get());
            return productToBeUpdated.get();
        } else {
            ResponseEntity.badRequest().body("Product not found");
            return null;
        }
    }

    //Delete product
    @DeleteMapping(value = "/deleteProduct/{id}")
    public void deleteProduct(@PathVariable(value = "id") Long id) {
        if(productServ.findById(id).isPresent()) productServ.deleteById(id);
        else ResponseEntity.badRequest().body("Product not found");
    }
}
