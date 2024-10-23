package com.example.codingexercise.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ProductPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String id;
    private String name;
    private String description;
    private List<String> productIds;
    private Double price;

    public ProductPackage(String id, String name, String description, List<String> productIds, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productIds = productIds;
        this.price = price;
    }

}
