package com.rig.customerservice.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
public final class Book extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "genre", length = 50, nullable = false)
    private String genre;

    @Column(name = "author", length = 100, nullable = false)
    private String author;
    
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "pages", nullable = false)
    private Integer pages;
    
    @Column(name = "price", precision = 2, nullable = false)
    private Double price;
    
    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "sold_quantity", nullable = false)
    private Integer soldQuantity;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orders;

}
