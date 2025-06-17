package com.kush.shoppingkart.controllers;

import com.kush.shoppingkart.Service.Implementation.ShopUserDetails;
import com.kush.shoppingkart.configurations.jwt.JWTUtils;
import com.kush.shoppingkart.repository.UserRepository;
import com.kush.shoppingkart.request.LoginRequest;
import com.kush.shoppingkart.response.ApiResponse;
import com.kush.shoppingkart.response.JwtResponse;
import com.kush.shoppingkart.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
        try {
            System.out.println("=== LOGIN ATTEMPT STARTED ===");
            System.out.println("Login attempt for email: " + request.getEmail());
            System.out.println("Password provided: " + request.getPassword());
            
            // Check if user exists in database first
            boolean userExists = userRepository.existsByEmail(request.getEmail());
            System.out.println("User exists in database: " + userExists);
            
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    ));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokerForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);

            return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));
        } catch (AuthenticationException e) {
            System.out.println("=== AUTHENTICATION FAILED ===");
            System.out.println("Authentication failed: " + e.getMessage());
            System.out.println("Exception type: " + e.getClass().getSimpleName());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Bad credentials", null));
        }
    }
    
    @GetMapping("/debug/users")
    public ResponseEntity<ApiResponse> getAllUsers(){
        // This is just for debugging - remove in production
        return ResponseEntity.ok(new ApiResponse("Users found", userRepository.findAll()));
    }

    @PostMapping("/debug/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String newPassword = passwordEncoder.encode("12345");
            user.setPassword(newPassword);
            userRepository.save(user);
            System.out.println("Password reset for " + email + " to: 12345");
            System.out.println("New hash: " + newPassword);
            return ResponseEntity.ok(new ApiResponse("Password reset successful", null));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("User not found", null));
    }

}
