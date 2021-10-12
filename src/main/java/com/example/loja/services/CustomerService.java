package com.example.loja.services;

import com.example.loja.models.Customer;
import com.example.loja.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepo;


    public Optional<Customer> findById(Long aLong) {
        return customerRepo.findById(aLong);
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public void deleteById(Long aLong) {
        customerRepo.deleteById(aLong);
    }

    public <S extends Customer> S save(S entity) {
        return customerRepo.save(entity);
    }

}
