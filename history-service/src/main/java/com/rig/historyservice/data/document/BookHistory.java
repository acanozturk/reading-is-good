package com.rig.historyservice.data.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "book_history")
@Data
public final class BookHistory {

    @Id
    private String id;
    
    @Indexed
    private Long bookId;
    
    private String title;

    private String genre;

    private String author;

    private String publishDate;

    private Integer pages;

    private Double price;
   
    private Integer totalQuantity;

    private Integer soldQuantity;

    private Integer availableQuantity;

    @CreatedDate
    private Instant createdAt;
    
}
