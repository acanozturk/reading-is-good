package com.rig.customerservice.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "customer_address")
@Getter
@Setter
public final class CustomerAddress extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "country", length = 100, nullable = false)
    private String country;

    @Column(name = "city", length = 100, nullable = false)
    private String city;
    
    @Column(name = "district", length = 100, nullable = false)
    private String district;
    
    @Column(name = "street", length = 100, nullable = false)
    private String street;
    
    @Column(name = "house_number", length = 20, nullable = false)
    private String houseNumber;

    @Column(name = "post_code", length = 20, nullable = false)
    private String postCode;
    
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonManagedReference
    private Customer customer;

    @OneToMany(mappedBy = "deliveryAddress", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orders;

}
