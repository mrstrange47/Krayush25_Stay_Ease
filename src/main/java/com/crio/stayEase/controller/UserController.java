package com.crio.stayEase.controller;

import com.crio.stayEase.dto.request.AuthRequest;
import com.crio.stayEase.dto.request.RegisterRequest;
import com.crio.stayEase.dto.response.AuthResponse;
import com.crio.stayEase.entity.User;
import com.crio.stayEase.exception.UsernameAlreadyExistException;
import com.crio.stayEase.service.AuthService;
import com.crio.stayEase.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(authService.register(request));
        AuthResponse authResponse= null;
        try{
            authResponse = authService.register(request);
        }
        catch(UsernameAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // GET /users
    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }
}
