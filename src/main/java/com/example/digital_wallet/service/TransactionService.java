package com.example.digital_wallet.service;

import java.util.Set;


import org.springframework.http.ResponseEntity;

import com.example.digital_wallet.model.Transaction;

public interface TransactionService {
    ResponseEntity<Transaction> transferMoney(String senderEmail,String receiverEmail,Double amount, String description);
    Set<Transaction> getReceivedTransactionHistory(String email);
    Set<Transaction> getSentTransactionHistory(String email);
    Set<Transaction> getHistoryByEmail(String email);
}
