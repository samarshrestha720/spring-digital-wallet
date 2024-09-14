package com.example.digital_wallet.model;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "sender_wallet_id")
    @JsonIgnore
    private Wallet senderWallet;

    @ManyToOne
    @JoinColumn(name = "receiver_wallet_id")
    @JsonIgnore
    private Wallet receiverWallet;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;
    private LocalDateTime timestamp;

    private String description;
    
    private Double senderBalanceAfter;

    private Double receiverBalanceAfter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Wallet getSenderWallet() {
        return senderWallet;
    }

    public void setSenderWallet(Wallet senderWallet) {
        this.senderWallet = senderWallet;
    }

    public Wallet getReceiverWallet() {
        return receiverWallet;
    }

    public void setReceiverWallet(Wallet receiverWallet) {
        this.receiverWallet = receiverWallet;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(TransactionType transaction_type) {
        this.transaction_type = transaction_type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSenderBalanceAfter() {
        return senderBalanceAfter;
    }

    public void setSenderBalanceAfter(Double senderBalanceAfter) {
        this.senderBalanceAfter = senderBalanceAfter;
    }

    public Double getReceiverBalanceAfter() {
        return receiverBalanceAfter;
    }

    public void setReceiverBalanceAfter(Double receiverBalanceAfter) {
        this.receiverBalanceAfter = receiverBalanceAfter;
    }

    
}
