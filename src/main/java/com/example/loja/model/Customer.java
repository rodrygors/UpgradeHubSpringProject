package com.example.loja.model;

import com.example.loja.controller.response.PurchaseReturnResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Min(value = 0)
    private int age;

    @OneToMany(mappedBy = "customer")
    private List<Purchase> purchases;

    @JsonIgnore
    public List<PurchaseReturnResponse> getPurchasesResponse() {
        List<PurchaseReturnResponse> purchasesResponse = new ArrayList<>();

        for (Purchase purchase : purchases) {
            PurchaseReturnResponse purchaseResp = new PurchaseReturnResponse();
            purchaseResp.createResponseFromPurchase(purchase);
            purchasesResponse.add(purchaseResp);
        }
        return purchasesResponse;
    }
}
