package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.DTOs.AuthRequest;
import com.codeBuddy.codeBuddy_Backend.DTOs.UserSignUpDTO;
import com.codeBuddy.codeBuddy_Backend.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/add")
    private ResponseEntity<?> addUser(@RequestBody UserSignUpDTO userDetails){
        return authService.addUser(userDetails);

    }

    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest){
        return authService.verifyUser(authRequest);
    }

    @PostMapping("/Refresh-Access-Token")
    private ResponseEntity<?> refreshAccesstoken(@RequestBody Map<String,String> token){
        return authService.refreshAccessToken(token);
    }
}
