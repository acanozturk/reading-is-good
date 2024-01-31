package com.rig.bookservice.service;

import com.rig.bookservice.data.payload.request.CreateBookRequest;
import com.rig.bookservice.data.payload.request.UpdateBookStockRequest;

public interface BookService {
    
    void createBook(CreateBookRequest createBookRequest);
    
    void updateBookStock(UpdateBookStockRequest updateBookStockRequest);
    
}
