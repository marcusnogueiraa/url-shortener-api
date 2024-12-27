package com.marcusnogueiraa.urlshortener.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marcusnogueiraa.urlshortener.entities.User;
import com.marcusnogueiraa.urlshortener.exceptions.UserNotFoundException;
import com.marcusnogueiraa.urlshortener.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username) );
        
        return new UserDetailsImpl(user);
    }

    
}
