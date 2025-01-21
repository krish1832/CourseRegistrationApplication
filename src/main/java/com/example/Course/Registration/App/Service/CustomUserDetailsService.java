package com.example.Course.Registration.App.Service;
import com.example.Course.Registration.App.Entity.Admin;
import com.example.Course.Registration.App.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Load user from the database
        Optional<Admin> AdminEntityOptional = adminRepository.findByUsername(username);

        if (AdminEntityOptional.isPresent()) {
           Admin userEntity = AdminEntityOptional.get();

            // Here, we use a hardcoded role for simplicity. If your roles are stored in a separate table, you can join or map them accordingly.
            return User.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())  // This should be the hashed password in your DB
                    .build();
        } else {
            throw new RuntimeException("Admin not found");
        }
    }
}
