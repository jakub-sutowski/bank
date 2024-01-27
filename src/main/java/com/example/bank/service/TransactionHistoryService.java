package com.example.bank.service;

import com.example.bank.model.entity.TransactionHistory;
import com.example.bank.model.entity.User;
import com.example.bank.repository.TransactionHistoryRepository;
import com.example.bank.type.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {

    private final TransactionHistoryRepository transactionHistoryRepository;

    @Transactional
    public void saveTransactionHistory(User user, double amount, StatusCode statusCode) {
        TransactionHistory transactionHistory = TransactionHistory.builder()
                .user(user)
                .amount(amount)
                .transactionTime(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
        transactionHistoryRepository.save(transactionHistory);
    }
}
