package com.rig.historyservice.kafka.event.history;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class BookHistoryEvent {

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
    
}
