package com.billsplitter.repository;

import com.billsplitter.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByPayerId(String payerId);  // Change Long to String
    List<Transaction> findByGroupId(String groupId);  // Change Long to String
}
