package com.example.loja.service;

import com.example.loja.exception.ProductNotFound;
import com.example.loja.exception.PurchaseNotFound;
import com.example.loja.model.Product;
import com.example.loja.model.Purchase;
import com.example.loja.repositorie.ProductRepository;
import com.example.loja.repositorie.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final PurchaseRepository purchaseRepo;

    public ProductService(ProductRepository productRepo, PurchaseRepository purchaseRepo) {
        this.productRepo = productRepo;
        this.purchaseRepo = purchaseRepo;
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public List<Product> getAllProductsFromPurchase(Long purchaseId) {
        List<Product> products = new ArrayList<>();
        Purchase purchase = purchaseRepo.findById(purchaseId).orElseThrow(PurchaseNotFound::new);
        //Populate products list with all products from the found Purchase object
        products.addAll(purchase.getPurchase_product());
        return products;
    }

    public Product findById(Long aLong) {
        return productRepo.findById(aLong).orElseThrow(ProductNotFound::new);
    }

    public Product save(String description, Float price) {
        return productRepo.save(Product.builder().description(description).price(price).build());
    }

    public Product update(Long id, String description, float price) {
        //By using this.findById() product is guaranteed to exist
        Product product = this.findById(id);
        product.setDescription(description);
        product.setPrice(price);
        return productRepo.save(product);
    }

    public void deleteById(Long aLong) {
        Long productId = this.findById(aLong).getId();

        //Find all purchases with productId
        List<Purchase> purchases = purchaseRepo.findAll();
        for (Purchase purchase : purchases) { //Cycle through all purchases
            for (Product product : purchase.getPurchase_product()) { //Cycle through all products inside purchase
                if (Objects.equals(product.getId(), productId)) {
                    purchase.getPurchase_product().remove(product); //Remove product that matches productId
                    purchaseRepo.save(purchase);
                }
            }
        }
        productRepo.deleteById(aLong);
    }
}