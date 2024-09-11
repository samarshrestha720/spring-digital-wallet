package com.example.digital_wallet.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_wallet.model.Wallet;
import com.example.digital_wallet.repository.WalletRepository;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletRepository walletRepository;

    
    @GetMapping("/")
    public ResponseEntity<Wallet> getWallet(Authentication authentication){
        String email = (String)authentication.getPrincipal();
        if(email==null){
            return ResponseEntity.badRequest().build();
        }
        Wallet wallet = walletRepository.findByUserEmail(email);
        if(wallet==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(wallet);
    }
    
}
