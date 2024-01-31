package com.rig.historyservice.repository;

import com.rig.historyservice.data.document.CustomerHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerHistoryRepository extends MongoRepository<CustomerHistory, String> {
}
