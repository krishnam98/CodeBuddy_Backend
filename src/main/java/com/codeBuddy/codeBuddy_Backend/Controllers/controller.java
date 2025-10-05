package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.Services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

    @Autowired
    private AuthService service;

    @GetMapping("/")
    public String StartBackend(){
        return "Hello World!, Code Buddy Backend Started...";
    }

//    @GetMapping("/getuser")
//    public Users getUser(){
//        return service.getUser();
//    }
}
