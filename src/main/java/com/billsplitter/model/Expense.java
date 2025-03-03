package com.billsplitter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;
    private String userId;
    private String groupId;
    private String description;
    private double amount;
    private String paidBy;
    private List<String> sharedWith;
}
