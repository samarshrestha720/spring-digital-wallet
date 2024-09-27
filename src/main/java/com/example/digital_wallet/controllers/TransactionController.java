package com.example.digital_wallet.controllers;

import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_wallet.model.Transaction;
import com.example.digital_wallet.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/test")
    public String getTest(@RequestParam String testParam) {
        return new String(testParam);
    }

    @PostMapping("/checkbeforetransfer")
    public ResponseEntity<String> checkBeforeTransfer(@RequestParam String receiverEmail, @RequestParam Double amount,
            @RequestParam String description, Authentication authentication) {
        String senderEmail = (String) authentication.getPrincipal();
        return transactionService.checkBeforeTransfer(senderEmail, receiverEmail, amount, description);

    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam String receiverEmail, @RequestParam Double amount,
            @RequestParam String description, String pin, Authentication authentication) {
        String senderEmail = (String) authentication.getPrincipal();
        return transactionService.transferMoney(senderEmail, receiverEmail, amount, description, pin);
    }

    @GetMapping("/senthistory")
    public ResponseEntity<Set<Transaction>> getSentTransactionHistory(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        Set<Transaction> sentHistory = transactionService.getSentTransactionHistory(email);
        return ResponseEntity.ok(sentHistory);
    }

    @GetMapping("/receivedhistory")
    public ResponseEntity<Set<Transaction>> getReceiveTransactionHistory(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        Set<Transaction> receivedHistory = transactionService.getReceivedTransactionHistory(email);
        return ResponseEntity.ok(receivedHistory);
    }

    @GetMapping("/allhistory")
    public ResponseEntity<Set<Transaction>> getHistory(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        Set<Transaction> transactions = transactionService.getHistoryByEmail(email);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("")
    public ResponseEntity<Optional<Transaction>> getTransactionById(@RequestParam String id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
}
