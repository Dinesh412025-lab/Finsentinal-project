package com.finsentinel.data;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Service
public class TransactionGenerator {

    private final Random random = new Random();
    private final String[] GATEWAYS = {"Razorpay", "Stripe", "Bank Transfer"};
    private long counter = 1000;

    public TransactionRecord generateNext() {
        boolean isAnomaly = random.nextDouble() < 0.15; // 15% chance of mismatch
        double amount = 500 + (50000 - 500) * random.nextDouble();
        
        return TransactionRecord.builder()
                .id("TXN-" + (++counter))
                .amount(Math.round(amount * 100.0) / 100.0)
                .gateway(GATEWAYS[random.nextInt(GATEWAYS.length)])
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .isAnomaly(isAnomaly)
                .status("Pending")
                .build();
    }
}
