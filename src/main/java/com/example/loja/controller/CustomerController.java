package com.example.loja.controller;

import com.example.loja.controller.request.CustomerCreationRequest;
import com.example.loja.controller.response.CustomerReturnResponse;
import com.example.loja.model.Customer;
import com.example.loja.service.CustomerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class CustomerController {

    private final CustomerService customerServ;

    public CustomerController(CustomerService customerServ) {
        this.customerServ = customerServ;
    }


    //Get all customers
    @GetMapping("/customers")
    public List<CustomerReturnResponse> getCustomers() {
        List<CustomerReturnResponse> customersRetTemp = new ArrayList<>();
        List<Customer> customers = customerServ.findAll();
        //Transforms the returned List<Customer> from customerServ.findAll() into a List<CustomerReturnResponse> and returns it
        if(!customers.isEmpty()){
            for (Customer customer : customers) {
                customersRetTemp.add(new CustomerReturnResponse()
                        .createResponseFromCustomer(customer));
            }
            return customersRetTemp;
        }
        return Collections.emptyList();
    }

    //Get customer by id
    @GetMapping("/customers/{id}")
    public CustomerReturnResponse getCustomersById(@PathVariable(value = "id") Long id) {
        Customer customer = customerServ.findById(id);
        //Transforms the returned Customer from customerServ.findById() into a CustomerReturnResponse and returns it
        return new CustomerReturnResponse()
                .createResponseFromCustomer(customer);
    }

    //Create customer
    @PostMapping(value = "/customers")
    public CustomerReturnResponse createCustomer(@RequestBody CustomerCreationRequest customerTemp) {
        //Transforms a CustomerCreationRequest received by parameter into a Customer that will be sent into customerServ.save
        Customer newCustomer = Customer.builder()
                .name(customerTemp.getName())
                .age(customerTemp.getAge())
                .build();
        customerServ.save(newCustomer);

        //Transforms the returned Customer from customerServ.save() into a CustomerReturnResponse and returns it
        return new CustomerReturnResponse()
                .createResponseFromCustomer(newCustomer);
    }

    //Update customer
    @PutMapping(value = "customers/{id}")
    public CustomerReturnResponse updateCustomer(@PathVariable(value = "id") Long id, @RequestBody CustomerCreationRequest customerReq) {
        Customer customer = customerServ.update(customerReq, id);
        //Transforms the returned Customer from customerServ.update() into a CustomerReturnResponse and returns it
        return new CustomerReturnResponse()
                .createResponseFromCustomer(customer);
    }

    //Delete customer
    @DeleteMapping(value = "/customers/{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id) {
        customerServ.deleteById(id);
    }
}