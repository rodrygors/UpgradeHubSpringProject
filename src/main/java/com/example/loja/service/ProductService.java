package com.example.loja.service;

import com.example.loja.exception.ProductNotFound;
import com.example.loja.model.Product;
import com.example.loja.model.Purchase;
import com.example.loja.repositorie.ProductRepository;
import com.example.loja.repositorie.PurchaseRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<Purchase> purchase = purchaseRepo.findById(purchaseId);

        if (purchase.isPresent()) {
            for (var product : purchase.get().getPurchase_product()) {
                products.add(product);
            }
            return products;
        }
        return null;
    }

    public Product findById(Long aLong) {
        return productRepo.findById(aLong).orElseThrow(ProductNotFound::new);
    }

    public <S extends Product> S save(S entity) {
        return productRepo.save(entity);
    }

    public Product update(Long id, String description, float price) {
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