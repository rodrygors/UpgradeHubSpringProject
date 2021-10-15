package com.example.loja.controller;

import com.example.loja.model.Customer;
import com.example.loja.controller.request.CustomerCreationRequest;
import com.example.loja.service.CustomerService;
import com.example.loja.controller.response.CustomerReturnResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        for(Customer customer : customers) {
            customersRetTemp.add(new CustomerReturnResponse()
                    .createResponseFromCustomer(customer));
        }
        return customersRetTemp;
    }

    //Get
    @GetMapping("/customers/{id}")
    public CustomerReturnResponse getCustomersById(@PathVariable(value = "id") Long id) {
        Customer customer = customerServ.findById(id);

        if(customer != null){
            return new CustomerReturnResponse()
                    .createResponseFromCustomer(customer);
        }
        return null;
    }

    //Create customer
    @PostMapping(value = "/customers")
    public CustomerReturnResponse createCustomer(@RequestBody CustomerCreationRequest customerTemp) {
        Customer newCustomer = Customer.builder()
                .name(customerTemp.getName())
                .age(customerTemp.getAge())
                .build();
        customerServ.save(newCustomer);

        CustomerReturnResponse customerRetResp = new CustomerReturnResponse()
                .createResponseFromCustomer(newCustomer);
        return customerRetResp;
    }

    //Update customer
    @PutMapping(value = "customers/{id}")
    public CustomerReturnResponse updateCustomer(@PathVariable(value = "id") Long id, @RequestBody CustomerCreationRequest customerReq) {
        Customer customer = customerServ.update(customerReq, id);
        CustomerReturnResponse customerRetResp = new CustomerReturnResponse()
                .createResponseFromCustomer(customer);
        return customerRetResp;
    }

    //Delete customer
    @DeleteMapping(value = "/customers/{id}")
    public void deleteCustomer(@PathVariable(value = "id") Long id){
        customerServ.deleteById(id);
    }
}