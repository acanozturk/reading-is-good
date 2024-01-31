package com.rig.historyservice.data.document;

import com.rig.historyservice.data.Book;
import com.rig.historyservice.data.Customer;
import com.rig.historyservice.data.CustomerAddress;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "order_history")
@Data
public final class OrderHistory {

    @Id
    private String id;

    @Indexed
    private Long orderId;
    
    private String orderCode;
    
    private Integer orderQuantity;
    
    private Double orderPrice;
    
    private String status;
    
    private Book book;
    
    private Customer customer;
    
    private CustomerAddress deliveryAddress;;

    @CreatedDate
    private Instant createdAt;
    
}
