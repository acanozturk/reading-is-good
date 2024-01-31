package com.rig.historyservice.repository;

import com.rig.historyservice.data.document.BookHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookHistoryRepository extends MongoRepository<BookHistory, String> {
}
