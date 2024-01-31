package com.rig.historyservice.kafka.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class BookEvent {

    private Long bookId;
    private String title;
    private String genre;
    private String author;
    private String publishDate;

}
