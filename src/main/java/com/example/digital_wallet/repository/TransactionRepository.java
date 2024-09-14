package com.example.digital_wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


import com.example.digital_wallet.model.Transaction;
import com.example.digital_wallet.model.Wallet;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String>{

    // List<Transaction> findBySenderWalletOrReceiverWallet(Wallet wallet);

} 