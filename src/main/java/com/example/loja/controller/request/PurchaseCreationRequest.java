package com.example.loja.controller.request;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseCreationRequest {
    private Boolean isPaid;
}
