package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.DTOs.AuthRequest;
import com.codeBuddy.codeBuddy_Backend.DTOs.UserSignUpDTO;
import com.codeBuddy.codeBuddy_Backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    private ResponseEntity<?> addUser(@RequestBody UserSignUpDTO userDetails){
        return userService.addUser(userDetails);

    }

    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest){
        return userService.verifyUser(authRequest);
    }
}
