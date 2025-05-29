package com.java.lessons.crud1.spring_la_mia_pizzeria_crud.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.model.User;
import com.java.lessons.crud1.spring_la_mia_pizzeria_crud.repositories.UserRepository;

@Service
public class DatabaseUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userAttempt = userRepository.findByUsername(username);
        if (userAttempt.isEmpty()) {
            throw new UsernameNotFoundException("no username with this user: " + username);
        } 

        
        return new DatabaseUserDetails(userAttempt.get());
        
    }

}
