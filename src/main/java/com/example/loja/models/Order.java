package com.example.loja.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean isPaid;

    @OneToMany
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;
}