package com.example.digital_wallet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.digital_wallet.model.User;
import com.example.digital_wallet.model.UserPrincipal;
import com.example.digital_wallet.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);

        if(user!= null){
            return new UserPrincipal(user);
        }   
        throw new UsernameNotFoundException("User not found!");
    }


}
