package com.example.digital_wallet.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.digital_wallet.model.User;

@Service
public class UserService {

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(10);

    public User hashPassword(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }

    
}
