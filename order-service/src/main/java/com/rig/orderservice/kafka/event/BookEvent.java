package com.rig.orderservice.kafka.event;

import com.rig.orderservice.data.entity.Book;
import lombok.Getter;

@Getter
public final class BookEvent {

    private Long bookId;
    private String title;
    private String genre;
    private String author;
    private String publishDate;

    public BookEvent from(final Book book) {
        this.bookId = book.getId();
        this.title = book.getTitle();
        this.genre = book.getGenre();
        this.author = book.getAuthor();
        this.publishDate = book.getPublishDate().toString();

        return this;
    }

}
