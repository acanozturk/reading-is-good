package com.rig.orderservice.data.payload;

import com.rig.orderservice.data.payload.response.AbstractResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public final class GetBookPayload extends AbstractResponse {
    
    private Long id;
    private String title;
    private String genre;
    private String author;
    private LocalDate publishDate;
    private Integer pages;

}
