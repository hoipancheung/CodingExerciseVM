package com.virgin.exercise.controller;

import com.virgin.exercise.model.Transaction;
import com.virgin.exercise.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/category/{category}")
    public List<Transaction> getTransactionsByCategory(@PathVariable String category) {
        return transactionService.getTransactionsByCategory(category);
    }

    @GetMapping("/total/{category}")
    public double getTotalOutgoingPerCategory(@PathVariable String category) {
        return transactionService.getTotalOutgoingPerCategory(category);
    }

    @GetMapping("/average/{category}")
    public double getMonthlyAverageSpend(@PathVariable String category) {
        return transactionService.getMonthlyAverageSpend(category);
    }

    @GetMapping("/highest/{category}/{year}")
    public double getHighestSpendInCategoryForYear(@PathVariable String category, @PathVariable int year) {
        return transactionService.getHighestSpendInCategoryForYear(category, year);
    }

    @GetMapping("/lowest/{category}/{year}")
    public double getLowestSpendInCategoryForYear(@PathVariable String category, @PathVariable int year) {
        return transactionService.getLowestSpendInCategoryForYear(category, year);
    }
}
