package com.finsentinel.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRecord {
    private String id;
    private double amount;
    private String gateway;
    private String timestamp;
    private boolean isAnomaly;
    private String status; // Matched, Mismatched, Pending
}
