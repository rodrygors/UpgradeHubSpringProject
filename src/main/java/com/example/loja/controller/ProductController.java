package com.example.loja.controller;

import com.example.loja.controller.request.ProductCreationResquest;
import com.example.loja.controller.response.ProductReturnResponse;
import com.example.loja.model.Product;
import com.example.loja.service.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ProductController {

    private final ProductService productServ;

    public ProductController(ProductService productServ) {
        this.productServ = productServ;
    }

    //Get all products
    @GetMapping("/products")
    public List<ProductReturnResponse> getProducts() {
        List<Product> products = productServ.findAll();
        List<ProductReturnResponse> productsResp = new ArrayList<>();
        if (!products.isEmpty()) {
            for (Product product : products) {
                productsResp.add(new ProductReturnResponse(
                        product.getId(),
                        product.getDescription(),
                        product.getPrice()
                ));
            }
            return productsResp;
        }
        //Exception
        return null;
    }

    //Get product by id
    @GetMapping("/products/{id}")
    public ProductReturnResponse getProductById(@PathVariable(value = "id") Long id) {
        Product product = productServ.findById(id);
        ProductReturnResponse productResp = new ProductReturnResponse(
                product.getId(),
                product.getDescription(),
                product.getPrice()
        );
        return productResp;
    }

    //Get products by purchase
    @GetMapping("/products/purchases/{id}")
    public List<ProductReturnResponse> getAllProductsFromPurchase(@PathVariable(value = "id") Long purchaseId) {
        List<Product> products = productServ.getAllProductsFromPurchase(purchaseId);
        List<ProductReturnResponse> productsRetResp = new ArrayList<>();

        for (Product product : products) {
            productsRetResp.add(new ProductReturnResponse(
                    product.getId(),
                    product.getDescription(),
                    product.getPrice()
            ));
        }
        return productsRetResp;
    }

    //Create product
    @PostMapping(value = "/products", consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody ProductCreationResquest productReq) {
        Product newProduct = Product.builder().description(productReq.getDescription()).price(productReq.getPrice()).build();
        return productServ.save(newProduct);
    }

    //Update product
    @PutMapping(value = "/products/{id}", consumes = "application/json", produces = "application/json")
    public ProductReturnResponse updateProduct(@PathVariable(value = "id") Long id, @RequestBody ProductCreationResquest productReq) {
        Product product = productServ.update(id, productReq.getDescription(), productReq.getPrice());
        return new ProductReturnResponse(
                id,
                product.getDescription(),
                product.getPrice()
        );
    }

    //Delete product
    @DeleteMapping(value = "/products/{id}")
    public void deleteProduct(@PathVariable(value = "id") Long id) {
        productServ.deleteById(id);
    }
}
