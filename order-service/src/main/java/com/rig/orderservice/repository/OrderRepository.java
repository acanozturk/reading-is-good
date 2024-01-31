package com.rig.orderservice.repository;

import com.rig.orderservice.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query(value = 
            "FROM Order " +
            "WHERE DATE(createdAt) BETWEEN :startDate AND :endDate"
    )
    List<Order> findAllBetween(LocalDate startDate, LocalDate endDate);
    
}
