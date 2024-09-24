package com.example.digital_wallet.service;

import java.time.LocalDateTime;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.digital_wallet.model.Transaction;
import com.example.digital_wallet.model.TransactionType;
import com.example.digital_wallet.model.Wallet;
import com.example.digital_wallet.repository.TransactionRepository;
import com.example.digital_wallet.repository.WalletRepository;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private WalletRepository walletRepository;

    @Autowired 
    private TransactionRepository transactionRepository;


    //TODO Refactor code. Seperate Wallet logic for reusability
    @Override
    public ResponseEntity<Transaction> transferMoney(String senderEmail, String receiverEmail, Double amount, String description) {
        Wallet senderWallet = walletRepository.findByUserEmail(senderEmail);
        Wallet receiverWallet = walletRepository.findByUserEmail(receiverEmail);
        if(senderWallet==null){
            return ResponseEntity.notFound().build();
        }
        if(receiverEmail==null){
            return ResponseEntity.notFound().build();
        }
        if(senderWallet.getBalance()<amount){
            return ResponseEntity.badRequest().build();
        }

        //Update wallet amounts
        senderWallet.setBalance(senderWallet.getBalance()-amount);
        receiverWallet.setBalance(receiverWallet.getBalance()+amount);

        //create new transaction
        Transaction transaction = new Transaction();
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setAmount(amount);

        transaction.setTransaction_type(TransactionType.TRANSFER);
        transaction.setDescription(description);
        transaction.setTimestamp(LocalDateTime.now());


        transaction.setSenderBalanceAfter(senderWallet.getBalance());
        transaction.setReceiverBalanceAfter(receiverWallet.getBalance());

        return ResponseEntity.ok(transactionRepository.save(transaction));
    }


    @Override
    public Set<Transaction> getReceivedTransactionHistory(String email) {
        Wallet wallet=walletRepository.findByUserEmail(email);
        return wallet.getRecievedTransactions();
    }


    @Override
    public Set<Transaction> getSentTransactionHistory(String email) {
        Wallet wallet = walletRepository.findByUserEmail(email);
        return wallet.getSentTransactions();
    }


    @Override
    public Set<Transaction> getHistoryByEmail(String email) {
        Wallet wallet = walletRepository.findByUserEmail(email);
        return transactionRepository.findBySenderWalletOrReceiverWallet(wallet, wallet);
    }


    @Override
    public ResponseEntity<String> checkBeforeTransfer(String senderEmail, String receiverEmail, Double amount,
            String description) {
            Wallet senderWallet = walletRepository.findByUserEmail(senderEmail);
            Wallet receiverWallet = walletRepository.findByUserEmail(receiverEmail);

            return ResponseEntity.ok().build();
    }

}
