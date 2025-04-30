package com.makeart.makeart_server.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_code", referencedColumnName = "code", nullable = false)
    @JsonBackReference
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_code", referencedColumnName = "code", nullable = false)
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_code", referencedColumnName = "code", nullable = false)
    @JsonBackReference
    private Subcategory subcategory;

    @Column(name = "imgPath", nullable = false)
    private String imgPath;

    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "costPrice", nullable = false)
    private Double costPrice;

    @Column(name = "salePrice", nullable = false)
    private Double salePrice;

    @Column(name = "details", nullable = false)
    private String details;
}
