package com.kush.shoppingkart.Service.Implementation;

import com.kush.shoppingkart.model.User;
import com.kush.shoppingkart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("=== UserDetailsService called ===");
        System.out.println("Attempting to load user by email: " + email);
        
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        System.out.println("User found: " + user.getEmail());
        System.out.println("User password hash: " + user.getPassword());
        System.out.println("User roles: " + user.getRoles());
        
        ShopUserDetails userDetails = ShopUserDetails.buildUserDetails(user);
        System.out.println("UserDetails created successfully");
        System.out.println("UserDetails username: " + userDetails.getUsername());
        System.out.println("UserDetails authorities: " + userDetails.getAuthorities());
        
        return userDetails;
    }
}