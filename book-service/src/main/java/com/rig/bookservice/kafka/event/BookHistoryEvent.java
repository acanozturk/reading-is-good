package com.rig.bookservice.kafka.event;

import com.rig.bookservice.data.entity.Book;
import lombok.Getter;

@Getter
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

    public BookHistoryEvent from(final Book book) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.genre = book.getGenre();
        this.author = book.getAuthor();
        this.publishDate = book.getPublishDate().toString();
        this.pages = book.getPages();
        this.price = book.getPrice();
        this.totalQuantity = book.getTotalQuantity();
        this.soldQuantity = book.getSoldQuantity();
        this.availableQuantity = book.getAvailableQuantity();

        return this;
    }

}
