package com.example.loja.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreationRequest {
    private String description;
    private float price;
    private Boolean isPaid;
}
