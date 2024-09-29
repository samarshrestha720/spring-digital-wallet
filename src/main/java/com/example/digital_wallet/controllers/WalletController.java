package com.example.digital_wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_wallet.model.Wallet;
import com.example.digital_wallet.repository.WalletRepository;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    @GetMapping("/")
    public ResponseEntity<Wallet> getWallet(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        if (email == null) {
            return ResponseEntity.badRequest().build();
        }
        Wallet wallet = walletRepository.findByUserEmail(email);
        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@RequestParam Double amount, Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        if (email == null) {
            return ResponseEntity.badRequest().body("Unauthorized!");
        }
        Wallet wallet = walletRepository.findByUserEmail(email);
        if (wallet == null) {
            return ResponseEntity.notFound().build();
        }
        Double finalAmount = wallet.getBalance() + amount;
        wallet.setBalance(finalAmount);
        walletRepository.save(wallet);
        return ResponseEntity.ok().build();

    }

    // TODO Refactor transfer endpoint. Add role based authentication(only admins
    // can perform transfer). Also, reuse the wallet service codes here.
    @Transactional
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestParam Double amount, @RequestParam String recieverEmail,
            Authentication authentication) {
        String senderEmail = (String) authentication.getPrincipal();
        if (senderEmail == null) {
            return ResponseEntity.badRequest().body("Unauthorized!");
        }
        Wallet senderWallet = walletRepository.findByUserEmail(senderEmail);
        Wallet receiverwallet = walletRepository.findByUserEmail(recieverEmail);
        if (senderWallet == null || receiverwallet == null) {
            return ResponseEntity.notFound().build();
        }
        Double senderBalance = senderWallet.getBalance();
        Double receiverBalance = receiverwallet.getBalance();

        if (senderBalance < amount) {
            return ResponseEntity.badRequest().body("Insufficient Balance");
        }
        senderWallet.setBalance(senderBalance - amount); // Deduct amount
        receiverwallet.setBalance(receiverBalance + amount); // Add amount
        walletRepository.save(senderWallet);
        walletRepository.save(receiverwallet);
        return ResponseEntity.ok().build();
    }

}
