package com.example.digital_wallet.controllers;

import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digital_wallet.model.Wallet;
import com.example.digital_wallet.repository.WalletRepository;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    public final WalletRepository walletRepository;

    public WalletController(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }

    @GetMapping("/")
    public ResponseEntity<String> getWallet(Authentication authentication){
        Object authDetails = authentication.getPrincipal();
        System.out.println(authDetails);
        // DocumentContext obj = JsonPath.parse(reqBody);
        // String id = obj.read("$.id");
        // System.out.println(id);
        // Optional<Wallet> wallet = walletRepository.findById(id);
        // if(!wallet.isPresent()){
        //     return ResponseEntity.notFound().build();
        // }
        return ResponseEntity.ok("Testing Phase");
    }
    
}
