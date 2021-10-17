package com.example.loja.controller.response;

import com.example.loja.model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerReturnResponse {
    private Long id;
    private String name;
    private int age;
    private List<PurchaseReturnResponse> purchasesResp;

    //Returns a CustomerReturnResponse with attributes taken from a Customer object passed by parameter
    @JsonIgnore
    public CustomerReturnResponse createResponseFromCustomer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.age = customer.getAge();
        if(customer.getPurchasesResponse() != null && !customer.getPurchasesResponse().isEmpty()){
            this.purchasesResp = customer.getPurchasesResponse();
        }
        else this.purchasesResp = new ArrayList<>();
        return this;
    }
}
