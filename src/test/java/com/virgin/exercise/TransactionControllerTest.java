package com.virgin.exercise;

import com.virgin.exercise.controller.TransactionController;
import com.virgin.exercise.exception.CategoryNotFoundException;
import com.virgin.exercise.exception.GlobalExceptionHandler;
import com.virgin.exercise.model.Transaction;
import com.virgin.exercise.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetTransactionsByCategory() throws Exception {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(LocalDate.of(2020, 11, 1), "Morrisons", "card", 10.40, "Groceries"),
                new Transaction(LocalDate.of(2020, 10, 1), "M&S", "card", 5.99, "Groceries")
        );

        when(transactionService.getTransactionsByCategory("Groceries")).thenReturn(transactions);

        mockMvc.perform(get("/transactions/category/Groceries")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vendor").value("Morrisons"))
                .andExpect(jsonPath("$[1].vendor").value("M&S"));
    }

    @Test
    public void testGetTransactionsByCategory_NotFound() throws Exception {
        when(transactionService.getTransactionsByCategory(anyString()))
                .thenThrow(new CategoryNotFoundException("Category not found: NonExistentCategory"));

        mockMvc.perform(get("/transactions/category/NonExistentCategory")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found: NonExistentCategory"));
    }

    @Test
    public void testGetTotalOutgoingPerCategory() throws Exception {
        when(transactionService.getTotalOutgoingPerCategory("MyMonthlyDD")).thenReturn(640.0);

        mockMvc.perform(get("/transactions/total/MyMonthlyDD")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("640.0"));
    }

    @Test
    public void testGetTotalOutgoingPerCategory_NotFound() throws Exception {
        when(transactionService.getTotalOutgoingPerCategory(anyString()))
                .thenThrow(new CategoryNotFoundException("Category not found: NonExistentCategory"));

        mockMvc.perform(get("/transactions/total/NonExistentCategory")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found: NonExistentCategory"));
    }

    @Test
    public void testGetMonthlyAverageSpend() throws Exception {
        when(transactionService.getMonthlyAverageSpend("Groceries")).thenReturn(8.195);

        mockMvc.perform(get("/transactions/average/Groceries")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("8.195"));
    }

    @Test
    public void testGetMonthlyAverageSpend_NotFound() throws Exception {
        when(transactionService.getMonthlyAverageSpend(anyString()))
                .thenThrow(new CategoryNotFoundException("Category not found: NonExistentCategory"));

        mockMvc.perform(get("/transactions/average/NonExistentCategory")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found: NonExistentCategory"));
    }
}
