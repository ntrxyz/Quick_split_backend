package com.billsplitter.repository;

import com.billsplitter.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByGroupId(String groupId);
    
    List<Expense> findByUserId(String userId);
}
