package com.example.digital_wallet.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private WalletService walletService;

    @Override
    public ResponseEntity<String> transferMoney(String senderEmail, String receiverEmail, Double amount,
            String description, String pin) {
        if (!walletService.isPinValid(senderEmail, pin)) {
            return ResponseEntity.badRequest().body("Incorrect Pin");
        }
        if (!walletService.isWalletValid(senderEmail)) {
            return new ResponseEntity<String>("Sender Email Not Found", HttpStatus.NOT_FOUND);
        }
        if (!walletService.isWalletValid(receiverEmail)) {
            return new ResponseEntity<String>("REceiver Email Not Found", HttpStatus.NOT_FOUND);
        }
        if (!walletService.hasSufficientBalance(senderEmail, amount)) {
            return ResponseEntity.badRequest().body("Insufficient Balance");
        }

        // Update wallet amounts
        walletService.updateWalletBalances(senderEmail, receiverEmail, amount);

        // create new transaction
        Transaction transaction = createNewTransaction(senderEmail, receiverEmail, amount, description);
        return ResponseEntity.ok("Transaction successful with id: " + transaction.getId());
    }

    @Override
    public Set<Transaction> getReceivedTransactionHistory(String email) {

        return walletService.getRecievedTransactions(email);
    }

    @Override
    public Set<Transaction> getSentTransactionHistory(String email) {
        return walletService.getSentTransactions(email);
    }

    @Override
    public Set<Transaction> getHistoryByEmail(String email) {
        Wallet wallet = walletRepository.findByUserEmail(email);
        return transactionRepository.findBySenderWalletOrReceiverWallet(wallet, wallet);
    }

    @Override
    public ResponseEntity<String> checkBeforeTransfer(String senderEmail, String receiverEmail, Double amount,
            String description) {
        if (!walletService.isWalletValid(senderEmail)) {
            return new ResponseEntity<String>("Sender Email Invalid", HttpStatus.NOT_FOUND);
        }
        if (!walletService.isWalletValid(receiverEmail)) {
            return new ResponseEntity<String>("Receiver Email Invalid", HttpStatus.NOT_FOUND);
        }
        if (!walletService.hasSufficientBalance(senderEmail, amount)) {
            return ResponseEntity.badRequest().body("Insufficient Funds");
        }

        return ResponseEntity.ok("Valid to Transfer");
    }

    @Override
    public Transaction createNewTransaction(String senderEmail, String receiverEmail, Double amount,
            String description) {
        Transaction transaction = new Transaction();
        transaction.setSenderWallet(walletService.findWalletByEmail(senderEmail));
        transaction.setReceiverWallet(walletService.findWalletByEmail(receiverEmail));
        transaction.setAmount(amount);

        transaction.setTransaction_type(TransactionType.TRANSFER);
        transaction.setDescription(description);
        transaction.setTimestamp(LocalDateTime.now());

        transaction.setSenderBalanceAfter(walletService.findWalletByEmail(senderEmail).getBalance());
        transaction.setReceiverBalanceAfter(walletService.findWalletByEmail(receiverEmail).getBalance());

        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

}
