package com.example.digital_wallet.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.digital_wallet.model.Transaction;
import com.example.digital_wallet.model.Wallet;
import com.example.digital_wallet.repository.WalletRepository;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Override
    public Wallet findWalletByEmail(String email) {
        return walletRepository.findByUserEmail(email);
    }

    @Override
    public boolean hasSufficientBalance(String email, Double amount) {
        if (findWalletByEmail(email).getBalance() < amount) {
            return false;
        }
        return true;
    }

    @Override
    public Wallet updateWalletBalances(String senderEmail, String receiverEmail, Double amount) {
        Wallet senderWallet = findWalletByEmail(senderEmail);
        Wallet receiverWallet = findWalletByEmail(receiverEmail);
        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);
        return senderWallet;
    }

    @Override
    public boolean isWalletValid(String email) {
        if (findWalletByEmail(email) == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isPinValid(String email, String pin) {
        Wallet wallet = findWalletByEmail(email);
        return wallet.getPin().equals(pin);
    }

    @Override
    public Set<Transaction> getRecievedTransactions(String email) {
        return findWalletByEmail(email).getRecievedTransactions();
    }

    @Override
    public Set<Transaction> getSentTransactions(String email) {
        return findWalletByEmail(email).getSentTransactions();
    }

}
