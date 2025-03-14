package com.virgin.exercise;

import com.virgin.exercise.exception.CategoryNotFoundException;
import com.virgin.exercise.model.Transaction;
import com.virgin.exercise.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testGetTransactionsByCategory() {
        List<Transaction> transactions = transactionService.getTransactionsByCategory("Groceries");
        assertEquals(2, transactions.size());
        assertEquals("Morrisons", transactions.get(0).getVendor());
        assertEquals("M&S", transactions.get(1).getVendor());
    }

    @Test
    public void testGetTransactionsByCategory_NotFound() {
        assertThrows(CategoryNotFoundException.class, () -> {
            transactionService.getTransactionsByCategory("NonExistentCategory");
        });
    }

    @Test
    public void testGetTotalOutgoingPerCategory() {
        double total = transactionService.getTotalOutgoingPerCategory("MyMonthlyDD");
        assertEquals(640.0, total);
    }

    @Test
    public void testGetTotalOutgoingPerCategory_NotFound() {
        assertThrows(CategoryNotFoundException.class, () -> {
            transactionService.getTotalOutgoingPerCategory("NonExistentCategory");
        });
    }

    @Test
    public void testGetMonthlyAverageSpend() {
        double average = transactionService.getMonthlyAverageSpend("Groceries");
        assertEquals(8.195, average, 0.001);
    }

    @Test
    public void testGetMonthlyAverageSpend_NotFound() {
        assertThrows(CategoryNotFoundException.class, () -> {
            transactionService.getMonthlyAverageSpend("NonExistentCategory");
        });
    }

    @Test
    public void testGetHighestSpendInCategoryForYear() {
        double highest = transactionService.getHighestSpendInCategoryForYear("Groceries", 2020);
        assertEquals(10.40, highest);
    }

    @Test
    public void testGetHighestSpendInCategoryForYear_NotFound() {
        assertThrows(CategoryNotFoundException.class, () -> {
            transactionService.getHighestSpendInCategoryForYear("NonExistentCategory", 2020);
        });
    }

    @Test
    public void testGetLowestSpendInCategoryForYear() {
        double lowest = transactionService.getLowestSpendInCategoryForYear("Groceries", 2020);
        assertEquals(5.99, lowest);
    }

    @Test
    public void testGetLowestSpendInCategoryForYear_NotFound() {
        assertThrows(CategoryNotFoundException.class, () -> {
            transactionService.getLowestSpendInCategoryForYear("NonExistentCategory", 2020);
        });
    }
}