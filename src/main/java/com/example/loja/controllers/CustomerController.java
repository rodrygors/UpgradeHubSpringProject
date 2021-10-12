package com.example.loja.controllers;

import com.example.loja.models.Customer;
import com.example.loja.requests.CustomerCreationRequest;
import com.example.loja.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Validated
public class CustomerController {
    @Autowired
    CustomerService customerServ;

    //Get all customers
    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerServ.findAll();
    }

    //Get
    @GetMapping("/customers/{id}")
    public Optional<Customer> getCustomersById(@PathVariable(value = "id") Long id) {
        return customerServ.findById(id);
    }

    //Create customer
    @PostMapping(value = "/customers")
    public Customer createCustomer(@RequestBody CustomerCreationRequest customerReq) {
        Customer newCustomer = Customer.builder()
                .name(customerReq.getName())
                .age(customerReq.getAge())
                .build();
        customerServ.save(newCustomer);
        return newCustomer;
    }

    //Update customer
    @PutMapping(value = "customers/{id}")
    public Customer updateCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customer) {
        System.out.println(id);
        Optional<Customer> customerToBeUpdated = customerServ.findById(id);
        if (customerToBeUpdated.isPresent()) {
            customerToBeUpdated.get().setName(customer.getName());
            customerToBeUpdated.get().setAge(customer.getAge());
            customerServ.save(customerToBeUpdated.get());
            return customerToBeUpdated.get();
        } else {
            ResponseEntity.badRequest().body("Customer not found");
            return null;
        }
    }

    //Delete customer
    @DeleteMapping(value = "/customers/{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id){
        if(customerServ.findById(id).isPresent()) customerServ.deleteById(id);
        else ResponseEntity.badRequest().body("Customer not found");
    }
}