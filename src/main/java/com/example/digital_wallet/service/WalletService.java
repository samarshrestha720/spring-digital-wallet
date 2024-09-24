package com.example.digital_wallet.service;

import com.example.digital_wallet.model.Wallet;

//TODO add more wallet services that are used in the transferMoney method
public interface WalletService {
    // Method to find and validate the wallet
    Wallet findWalletByEmail(String email);
    // Method to validate if the sender has enough balance
    boolean hasSufficientBalance(Wallet wallet, Double amount);
}
