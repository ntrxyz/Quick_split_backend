package com.billsplitter.service;

import com.billsplitter.model.Expense;
import com.billsplitter.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense) {
        // If createdAt is null, set it to the current timestamp.
        if (expense.getCreatedAt() == null) {
            expense.setCreatedAt(Instant.now());
        }
        if (expense.getSettledBy() == null) {
            expense.setSettledBy(new ArrayList<>());
        }
        return expenseRepository.save(expense);
    }

    public Optional<Expense> getExpenseById(String expenseId) {
        return expenseRepository.findById(expenseId);
    }

    public List<Expense> getExpensesByGroupId(String groupId) {
        return expenseRepository.findByGroupId(groupId);
    }

    public List<Expense> getExpensesByUserId(String userId) {
        return expenseRepository.findByUserId(userId);
    }

    public Expense updateExpense(String expenseId, Expense updatedExpense) {
        if (expenseRepository.existsById(expenseId)) {
            updatedExpense.setId(expenseId);
            // Preserve existing createdAt value if available.
            Optional<Expense> existingExpense = expenseRepository.findById(expenseId);
            existingExpense.ifPresent(exp -> updatedExpense.setCreatedAt(exp.getCreatedAt()));
            return expenseRepository.save(updatedExpense);
        }
        return null;
    }

    public boolean deleteExpense(String expenseId) {
        if (expenseRepository.existsById(expenseId)) {
            expenseRepository.deleteById(expenseId);
            return true;
        }
        return false;
    }

    public Expense markExpenseAsSettled(String expenseId, String userId) {
        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            List<String> settledBy = expense.getSettledBy();
            if (settledBy == null) {
                settledBy = new java.util.ArrayList<>();
            }
            if (!settledBy.contains(userId)) {
                settledBy.add(userId);
                expense.setSettledBy(settledBy);
                return expenseRepository.save(expense);
            }
        }
        return null;
    }

}