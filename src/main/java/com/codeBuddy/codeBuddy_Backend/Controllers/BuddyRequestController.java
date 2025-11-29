package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.DTOs.CreateBuddyRequestDTO;
import com.codeBuddy.codeBuddy_Backend.Model.UserPrincipal;
import com.codeBuddy.codeBuddy_Backend.Services.BuddyRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("buddy/request")
public class BuddyRequestController {
    @Autowired
    private BuddyRequestService buddyRequestService;

    @PostMapping("/sendRequest")
    public ResponseEntity<?> createRequest(@RequestBody CreateBuddyRequestDTO toUser, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return buddyRequestService.createRequest(toUser,userPrincipal);
    }

    @PutMapping("/acceptRequest/{reqId}")
    public ResponseEntity<?> acceptRequest(@PathVariable Long reqId){
        return buddyRequestService.acceptRequest(reqId);
    }

    @PutMapping("/rejectRequest/{reqId}")
    public ResponseEntity<?> rejectRequest(@PathVariable Long reqId){
        return buddyRequestService.rejectRequest(reqId);
    }

    @GetMapping("/incomingRequest")
    public ResponseEntity<?> getIncomingRequest(@AuthenticationPrincipal UserPrincipal userPrincipal){
       return buddyRequestService.getIncomingRequest(userPrincipal.getUser());
    }


}
