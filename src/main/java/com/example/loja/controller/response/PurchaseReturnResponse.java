package com.example.loja.controller.response;

import com.example.loja.model.Purchase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseReturnResponse {
    private Long id;
    private Boolean isPaid;
    private Long customerId;
    private String customerName;
    private List<ProductReturnResponse> productsResp;

    @JsonIgnore
    public PurchaseReturnResponse createResponseFromPurchase(Purchase purchase) {
        this.id = purchase.getId();
        this.isPaid = purchase.getIsPaid();
        this.customerId = purchase.getCustomer().getId();
        this.customerName = purchase.getCustomer().getName();
        if(purchase.getProductsResponse() != null && !purchase.getProductsResponse().isEmpty()){
            this.productsResp = purchase.getProductsResponse();
        }
        else this.productsResp = Collections.emptyList();

        return this;
    }
}