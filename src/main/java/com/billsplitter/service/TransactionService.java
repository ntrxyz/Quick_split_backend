package com.billsplitter.service;

import com.billsplitter.model.Transaction;
import com.billsplitter.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction recordPayment(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getUserTransactions(String userId) {  // Change Long to String
        return transactionRepository.findByPayerId(userId);
    }

    public List<Transaction> getGroupTransactions(String groupId) {  // Change Long to String
        return transactionRepository.findByGroupId(groupId);
    }

    public Double getUserBalance(String userId) {  // Change Long to String
        List<Transaction> transactions = transactionRepository.findByPayerId(userId);
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }
}
