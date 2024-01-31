package com.rig.statisticsservice.repository;

import com.rig.statisticsservice.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query(value = 
            "SELECT " +
                    "TO_CHAR(DATE(created_at),'Month') AS \"month\", " +
                    "COUNT(id) AS total_order_count, " +
                    "SUM(order_quantity) AS total_book_count, " +
                    "SUM(order_price) AS total_purchased_amount " +
            "FROM orders " +
            "WHERE customer_id = :customerId " +
            "GROUP BY \"month\" " +
            "ORDER BY TO_DATE(TO_CHAR(DATE(created_at),'Month'),'Month') DESC",
            nativeQuery = true
    )
    List<Map<String, Object>> getMonthlyOrderStatisticsOfCustomer(long customerId);
    
}
