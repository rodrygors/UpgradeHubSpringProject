package com.example.loja.service;

import com.example.loja.controller.request.CustomerCreationRequest;
import com.example.loja.exception.CustomerNotFound;
import com.example.loja.model.Customer;
import com.example.loja.model.Purchase;
import com.example.loja.repositorie.CustomerRepository;
import com.example.loja.repositorie.PurchaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final PurchaseRepository purchaseRepo;

    public CustomerService(CustomerRepository customerRepo, PurchaseRepository purchaseRepo) {
        this.customerRepo = customerRepo;
        this.purchaseRepo = purchaseRepo;
    }

    public Customer findById(Long aLong) {
        return customerRepo.findById(aLong).orElseThrow(CustomerNotFound::new);
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public void deleteById(Long aLong) {
        Customer customerToDelete = this.findById(aLong);
        for (Purchase purchase : customerToDelete.getPurchases()) {
            purchaseRepo.deleteById(purchase.getId());
        }
        customerRepo.deleteById(aLong);
    }

    public <S extends Customer> S save(S entity) {
        return customerRepo.save(entity);
    }

    public Customer update(CustomerCreationRequest newCustomerReq, Long customerId) {
        Customer customer = this.findById(customerId);
        customer.setName(newCustomerReq.getName());
        customer.setAge(newCustomerReq.getAge());
        return customerRepo.save(customer);
    }
}
