package com.example.loja.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    @Min(value = 0)
    private float price;

    @ManyToMany(mappedBy = "purchase_product")
    private List<Purchase> purchases;

//    @JsonIgnore
//    public List<Long> getPurchaseIds(){
//        List<Long> purchaseIds = new ArrayList<>();
//
//        for (Purchase purchase : this.purchases){
//            purchaseIds.add(purchase.getId());
//        }
//        return purchaseIds;
//    }
}
