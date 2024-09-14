package com.example.digital_wallet.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Double balance;

    @OneToOne()
    @JoinColumn(name="user_id")
    @JsonManagedReference // Parent reference
    private User user;

    private String pin;

    @OneToMany(mappedBy = "senderWallet")
    private Set<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiverWallet")
    private Set<Transaction> recievedTransactions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Set<Transaction> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(Set<Transaction> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public Set<Transaction> getRecievedTransactions() {
        return recievedTransactions;
    }

    public void setRecievedTransactions(Set<Transaction> recievedTransactions) {
        this.recievedTransactions = recievedTransactions;
    }

    
}
