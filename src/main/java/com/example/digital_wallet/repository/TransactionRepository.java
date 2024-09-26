package com.example.digital_wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

import com.example.digital_wallet.model.Transaction;
import com.example.digital_wallet.model.Wallet;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Optional<Transaction> findById(String id);

    Set<Transaction> findBySenderWallet(Wallet wallet);

    Set<Transaction> findByReceiverWallet(Wallet wallet);

    Set<Transaction> findBySenderWalletOrReceiverWallet(Wallet wallet1, Wallet wallet2);

}