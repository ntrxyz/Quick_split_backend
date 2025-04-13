package com.billsplitter.repository;

import com.billsplitter.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findByGroupId(String groupId);

    List<Expense> findByUserId(String userId);

    // Retrieves expenses for a group ordered by creation date (most recent first)
    List<Expense> findByGroupIdOrderByCreatedAtDesc(String groupId);

    // Retrieves expenses for a user ordered by creation date (most recent first)
    List<Expense> findByUserIdOrderByCreatedAtDesc(String userId);

    // Retrieves expenses that were created between the specified start and end Instants.
    List<Expense> findByCreatedAtBetween(Instant start, Instant end);
}