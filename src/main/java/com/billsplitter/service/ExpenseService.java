package com.billsplitter.service;

import com.billsplitter.model.Expense;
import com.billsplitter.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(Expense expense) {
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
}