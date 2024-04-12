package com.crio.stayEase.service;

import com.crio.stayEase.dto.request.AuthRequest;
import com.crio.stayEase.dto.request.RegisterRequest;
import com.crio.stayEase.dto.response.AuthResponse;
import com.crio.stayEase.entity.User;
import com.crio.stayEase.enums.Role;
import com.crio.stayEase.exception.UsernameAlreadyExistException;
import com.crio.stayEase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        if(existingUser != null){
            throw new UsernameAlreadyExistException(request.getEmail() + " Already exists!");
        }
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
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
