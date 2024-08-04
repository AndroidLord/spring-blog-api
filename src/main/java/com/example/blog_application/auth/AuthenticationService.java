package com.example.blog_application.auth;

import com.example.blog_application.auth.DTO.request.AuthenticationRequestDTO;
import com.example.blog_application.auth.DTO.request.RegistrationRequestDTO;
import com.example.blog_application.auth.DTO.response.AuthenticationResponseDTO;
import com.example.blog_application.role.RoleRepository;
import com.example.blog_application.security.JwtService;
import com.example.blog_application.user.User;
import com.example.blog_application.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    // Defined in Bean Configuration
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public void register(RegistrationRequestDTO request) {
        var userRole = roleRepository.findByName("USER").orElseThrow(
                () -> new RuntimeException("Error: Role is not found.")
        );

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword() // Do not encode the password here
                    )
            );

            var user = (User) auth.getPrincipal();
            var claims = new HashMap<String, Object>();
            claims.put("fullName", user.getFullName());

            var jwtToken = jwtService.generateToken(claims, user);

            return AuthenticationResponseDTO.builder().token(jwtToken).build();

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email/password supplied");
        }
    }

    public void activateAccount(String accountActivationToken) {

    }
}
