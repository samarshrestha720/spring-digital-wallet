package com.example.digital_wallet.controllers;

import java.util.Set;

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
    

    //TODO complete endpoint after refactor of services are complete
    @PostMapping("/checkbeforetransfer")
    public ResponseEntity<String> checkBeforeTransfer(@RequestParam String receiverEmail, @RequestParam Double amount,@RequestParam String description, Authentication authentication){
        String senderEmail = (String)authentication.getPrincipal();

        return ResponseEntity.ok().body("Valid to Transfer");
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferMoney(@RequestParam String receiverEmail, @RequestParam Double amount,@RequestParam String description, Authentication authentication){
        String senderEmail = (String)authentication.getPrincipal();
        return transactionService.transferMoney(senderEmail, receiverEmail, amount, description);
    }

    @GetMapping("/senthistory")
    public ResponseEntity<Set<Transaction>> getSentTransactionHistory(@RequestParam String email){
        Set<Transaction> sentHistory = transactionService.getSentTransactionHistory(email);
        return ResponseEntity.ok(sentHistory);
    }

    @GetMapping("/allhistory")
    public ResponseEntity<Set<Transaction>> getHistory(Authentication authentication){
        String email = (String)authentication.getPrincipal();
        Set<Transaction> transactions = transactionService.getHistoryByEmail(email);
        return ResponseEntity.ok(transactions);
    }
}
