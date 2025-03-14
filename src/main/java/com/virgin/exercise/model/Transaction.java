package com.virgin.exercise.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {
    private LocalDate transactionDate;
    private String vendor;
    private String type;
    private double amount;
    private String category;
}
