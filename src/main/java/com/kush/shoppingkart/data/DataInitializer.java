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
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNoExits();
        createDefaultRoleOfNotExists(defaultRoles);
        createDefaultAdminIfNoExits();
    }

    private void createDefaultUserIfNoExits() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for(int i=1; i<=6; i++){
            String defaultEmail = "comedyKid"+i+"@gmail.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Comedy");
            user.setLastName("Kid"+i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("12345"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully");
        }
    }

    private void createDefaultAdminIfNoExits() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
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
            System.out.println("Default admin user " + i + " created successfully");
        }
    }

    private void createDefaultRoleOfNotExists(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
