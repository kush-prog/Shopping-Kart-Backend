package com.kush.shoppingkart.data;

import com.kush.shoppingkart.model.Role;
import com.kush.shoppingkart.model.User;
import com.kush.shoppingkart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

   @Override
public void onApplicationEvent(ApplicationReadyEvent event) {
    System.out.println("=== DATA INITIALIZER STARTED ===");
    Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
    createDefaultRoleOfNotExists(defaultRoles);
    createDefaultUserIfNoExits();
    createDefaultAdminIfNoExits();
    System.out.println("=== DATA INITIALIZER COMPLETED ===");
}

private void createDefaultUserIfNoExits() {
    System.out.println("Looking for ROLE_USER...");
    Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
    if (userRole == null) {
        System.out.println("ROLE_USER not found, skipping user creation");
        return;
    }
    System.out.println("ROLE_USER found, creating users...");
    
    for(int i=1; i<=6; i++){
        String defaultEmail = "comedyKid"+i+"@gmail.com";
        System.out.println("Checking if user exists: " + defaultEmail);
        if(userRepository.existsByEmail(defaultEmail)){
            System.out.println("User already exists: " + defaultEmail);
            continue;
        }
        User user = new User();
        user.setFirstName("Comedy");
        user.setLastName("Kid"+i);
        user.setEmail(defaultEmail);
        
        String plainPassword = "12345"; // Make sure this is exactly what you expect
        String hashedPassword = passwordEncoder.encode(plainPassword);
        
        System.out.println("Creating user with plain password: '" + plainPassword + "'");
        System.out.println("Hashed password: " + hashedPassword);
        
        user.setPassword(hashedPassword);
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        System.out.println("Default user " + i + " created successfully with email: " + defaultEmail);
    }
    }

    private void createDefaultAdminIfNoExits() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElse(null);
        if (adminRole == null) {
            System.out.println("ROLE_ADMIN not found, skipping admin creation");
            return;
        }
        
        for(int i=1; i<=2; i++){
            String defaultEmail = "adminKid"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Kid"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("comedy@12345"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully with email: " + defaultEmail);
        }
    }

    private void createDefaultRoleOfNotExists(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}