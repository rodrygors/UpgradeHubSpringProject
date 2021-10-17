package com.example.loja.controller.response;

import com.example.loja.model.Customer;
import com.example.loja.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReturnResponse {
    private Long id;
    private String description;
    private float price;

    //Returns a CustomerReturnResponse with attributes taken from a Customer object passed by parameter
    @JsonIgnore
    public ProductReturnResponse createProductReturnResponse(Product product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.price = product.getPrice();
        return this;
    }
}