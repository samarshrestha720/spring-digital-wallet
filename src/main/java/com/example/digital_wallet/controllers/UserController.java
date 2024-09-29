package com.example.digital_wallet.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.digital_wallet.model.User;
import com.example.digital_wallet.model.Wallet;
import com.example.digital_wallet.repository.UserRepository;
import com.example.digital_wallet.repository.WalletRepository;
import com.example.digital_wallet.service.UserService;
import com.example.digital_wallet.webtoken.JwtService;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserRepository userRepository, WalletRepository walletRepository, UserService userService,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/protected")
    public String rootGet() {
        return "User Controller Working!";
    }

    @GetMapping("/test")
    public String test() {
        return "Open Test Endpoint working!";
    }

    @GetMapping("/")
    public ResponseEntity<User> getUser(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        }

        return "User not authenticated";
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        User exist = userRepository.findByEmail(user.getEmail());
        if (exist != null) {
            return ResponseEntity.badRequest().build();
        }
        // save new user in DB
        User newUser = userRepository.save(userService.hashPassword(user));

        // create wallet for that user
        Wallet wallet = new Wallet();
        wallet.setBalance(0.0);
        wallet.setUser(newUser);

        walletRepository.save(wallet);

        return new ResponseEntity<User>(newUser, HttpStatusCode.valueOf(201));
    }

}
