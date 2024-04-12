package com.crio.stayEase.service;

import com.crio.stayEase.dto.request.AuthRequest;
import com.crio.stayEase.dto.request.RegisterRequest;
import com.crio.stayEase.dto.response.AuthResponse;
import com.crio.stayEase.entity.User;
import com.crio.stayEase.enums.Role;
import com.crio.stayEase.exception.UsernameAlreadyExistException;
import com.crio.stayEase.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) throws UsernameAlreadyExistException {
        if (request.getRole() == null) {
            request.setRole(Role.CUSTOMER);
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        User existingUser = userRepository.findByEmail(request.getEmail());
        log.info("Existing user: {}",existingUser);
        if(existingUser != null){
            log.error("duplicate email found");
            throw new UsernameAlreadyExistException(request.getEmail() + " Already exists!");
        }
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        log.info(user.getEmail() + " registered");
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();

    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

}
