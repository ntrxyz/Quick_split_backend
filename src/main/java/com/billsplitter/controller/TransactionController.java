package com.billsplitter.controller;

import com.billsplitter.model.Transaction;
import com.billsplitter.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/pay")
    public ResponseEntity<Transaction> recordPayment(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.recordPayment(transaction));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable String userId) {  // Change Long to String
        return ResponseEntity.ok(transactionService.getUserTransactions(userId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Transaction>> getGroupTransactions(@PathVariable String groupId) {  // Change Long to String
        return ResponseEntity.ok(transactionService.getGroupTransactions(groupId));
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<Double> getUserBalance(@PathVariable String userId) {  // Change Long to String
        return ResponseEntity.ok(transactionService.getUserBalance(userId));
    }
}
