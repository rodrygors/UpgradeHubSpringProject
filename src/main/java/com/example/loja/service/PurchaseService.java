package com.example.loja.service;

import com.example.loja.exception.CustomerNotFound;
import com.example.loja.exception.ProductNotFound;
import com.example.loja.exception.PurchaseNotFound;
import com.example.loja.model.Customer;
import com.example.loja.model.Product;
import com.example.loja.model.Purchase;
import com.example.loja.repositorie.CustomerRepository;
import com.example.loja.repositorie.ProductRepository;
import com.example.loja.repositorie.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepository;

    public PurchaseService(PurchaseRepository purchaseRepo, CustomerRepository customerRepo, ProductRepository productRepository) {
        this.purchaseRepo = purchaseRepo;
        this.customerRepo = customerRepo;
        this.productRepository = productRepository;
    }

    public List<Purchase> findAll() {
        return purchaseRepo.findAll();
    }


    public Optional<Purchase> getById(Long id) {
        return purchaseRepo.findById(id);
    }

    public Purchase findById(Long id) {
        return purchaseRepo.findById(id).orElseThrow(PurchaseNotFound::new);
    }

    public Purchase save(Boolean isPaid, Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(CustomerNotFound::new);
        Purchase purchase = new Purchase();
        purchase.setIsPaid(isPaid);
        purchase.setCustomer(customer);
        return purchaseRepo.save(purchase);
    }

    public Purchase addProductToPurchase(Long productId, Long purchaseId) {
        Purchase purchase = this.findById(purchaseId);
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFound::new);

        purchase.getPurchase_product().add(product);
        return purchaseRepo.save(purchase);
    }

    public void deleteById(Long id) {
        Purchase purchase = this.findById(id);

        //Remove purchase from it's customer's purchases list
        purchase.getCustomer().getPurchases().remove(purchase);
        customerRepo.save(purchase.getCustomer());
        purchaseRepo.deleteById(purchase.getId());

    }
}
