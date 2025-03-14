package com.virgin.exercise.service;

import com.virgin.exercise.exception.CategoryNotFoundException;
import com.virgin.exercise.model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final List<Transaction> transactions = Arrays.asList(
            new Transaction(LocalDate.of(2020, 11, 1), "Morrisons", "card", 10.40, "Groceries"),
            new Transaction(LocalDate.of(2020, 10, 28), "CYBG", "direct debit", 600.00, "MyMonthlyDD"),
            new Transaction(LocalDate.of(2020, 10, 28), "PureGym", "direct debit", 40.00, "MyMonthlyDD"),
            new Transaction(LocalDate.of(2020, 10, 1), "M&S", "card", 5.99, "Groceries"),
            new Transaction(LocalDate.of(2020, 9, 30), "McMillan", "internet", 10.00, null)
    );

    public List<Transaction> getTransactionsByCategory(String category) {
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(t -> category.equals(t.getCategory()))
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .collect(Collectors.toList());

        if (filteredTransactions.isEmpty()) {
            throw new CategoryNotFoundException("Category not found: " + category);
        }
        return filteredTransactions;
    }

    public double getTotalOutgoingPerCategory(String category) {
        double total = transactions.stream()
                .filter(t -> category.equals(t.getCategory()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        if (total == 0.0) {
            throw new CategoryNotFoundException("Category not found: " + category);
        }
        return total;
    }

    public double getMonthlyAverageSpend(String category) {
        List<Transaction> categoryTransactions = getTransactionsByCategory(category);
        if (categoryTransactions.isEmpty()) {
            throw new CategoryNotFoundException("Category not found: " + category);
        }

        double totalSpend = categoryTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        long months = categoryTransactions.stream()
                .map(t -> t.getTransactionDate().getYear() * 12 + t.getTransactionDate().getMonthValue())
                .distinct()
                .count();
        return totalSpend / months;
    }

    public double getHighestSpendInCategoryForYear(String category, int year) {
        OptionalDouble max = transactions.stream()
                .filter(t -> category.equals(t.getCategory()) && t.getTransactionDate().getYear() == year)
                .mapToDouble(Transaction::getAmount)
                .max();

        if (max.isEmpty()) {
            throw new CategoryNotFoundException("Category not found for the given year: " + category);
        }
        return max.getAsDouble();
    }

    public double getLowestSpendInCategoryForYear(String category, int year) {
        OptionalDouble min = transactions.stream()
                .filter(t -> category.equals(t.getCategory()) && t.getTransactionDate().getYear() == year)
                .mapToDouble(Transaction::getAmount)
                .min();

        if (min.isEmpty()) {
            throw new CategoryNotFoundException("Category not found for the given year: " + category);
        }
        return min.getAsDouble();
    }
}
