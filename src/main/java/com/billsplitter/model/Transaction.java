package com.billsplitter.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    private String id;

    private String payerId;  // Change from User object to String
    private String payeeId;  // Change from User object to String
    private String groupId;  // Change from Group object to String

    private Double amount;
    private LocalDateTime timestamp = LocalDateTime.now();
}
