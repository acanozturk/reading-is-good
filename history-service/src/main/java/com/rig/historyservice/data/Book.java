package com.rig.historyservice.data;

import lombok.Data;

@Data
public final class Book {

    private Long bookId;
    
    private String title;
    
    private String genre;
    
    private String author;
    
    private String publishDate;
    
}
