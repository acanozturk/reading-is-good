package com.rig.historyservice.data.document;

import com.rig.historyservice.data.Account;
import com.rig.historyservice.data.CustomerAddress;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "customer_history")
@Data
public final class CustomerHistory {

    @Id
    private String id;

    @Indexed
    private Long customerId;
    
    private String gender;
    
    private String firstName;
    
    private String lastName;
    
    private String dateOfBirth;
    
    private Account account;
    
    private CustomerAddress address;

    @CreatedDate
    private Instant createdAt;
    
}
