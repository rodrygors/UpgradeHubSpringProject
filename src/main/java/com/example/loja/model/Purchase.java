package com.example.loja.model;

import com.example.loja.controller.response.ProductReturnResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean isPaid;

    @ManyToMany
    @JoinTable(name = "purchase_product",
            joinColumns = @JoinColumn(name = "purchaseId"),
            inverseJoinColumns = @JoinColumn(name = "productId"))
    private List<Product> purchase_product;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @JsonIgnore
    public List<ProductReturnResponse> getProductsResponse() {
        List<ProductReturnResponse> productsResponse = new ArrayList<>();

        if(purchase_product != null && !purchase_product.isEmpty()){
            for (Product product : this.purchase_product) {
                productsResponse.add(new ProductReturnResponse(
                        product.getId(),
                        product.getDescription(),
                        product.getPrice()
                ));
            }
            return productsResponse;
        }
        return new ArrayList<>();
    }
}