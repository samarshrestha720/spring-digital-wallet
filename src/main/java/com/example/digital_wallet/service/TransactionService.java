package com.example.digital_wallet.service;

import java.util.Set;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.digital_wallet.model.Transaction;

public interface TransactionService {
        Transaction createNewTransaction(String senderEmail, String receiverEmail, Double amount, String description);

        ResponseEntity<String> checkBeforeTransfer(String senderEmail, String receiverEmail, Double amount,
                        String description);

        ResponseEntity<String> transferMoney(String senderEmail, String receiverEmail, Double amount,
                        String description, String pin);

        Set<Transaction> getReceivedTransactionHistory(String email);

        Set<Transaction> getSentTransactionHistory(String email);

        Set<Transaction> getHistoryByEmail(String email);

        Optional<Transaction> getTransactionById(String id);
}
