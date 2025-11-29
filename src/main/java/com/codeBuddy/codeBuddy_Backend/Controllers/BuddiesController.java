package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.Model.UserPrincipal;
import com.codeBuddy.codeBuddy_Backend.Services.BuddiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buddies")
@CrossOrigin(origins = "*")
public class BuddiesController {

    @Autowired
    private BuddiesService buddiesService;

    @GetMapping("/")
    public ResponseEntity<?> getBuddies(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return buddiesService.getBuddies(userPrincipal.getUser());

    }
}
