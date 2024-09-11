package com.example.digital_wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.digital_wallet.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {
}
