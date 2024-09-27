package com.example.digital_wallet.service;

import java.util.Set;

import com.example.digital_wallet.model.Transaction;
import com.example.digital_wallet.model.Wallet;

public interface WalletService {
    // check if wallet exists
    boolean isWalletValid(String email);

    // check pin number before transaction
    boolean isPinValid(String email, String pin);

    // Method to find and validate the wallet
    Wallet findWalletByEmail(String email);

    // Method to get received transactions in wallet
    Set<Transaction> getRecievedTransactions(String email);

    // Method to get sent transactions in wallet
    Set<Transaction> getSentTransactions(String email);

    // Method to validate if the sender has enough balance
    boolean hasSufficientBalance(String email, Double amount);

    // Method to update the wallet balance
    Wallet updateWalletBalances(String senderEmail, String receiverEmail, Double amount);
}
