package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.DTOs.UsersDTO;
import com.codeBuddy.codeBuddy_Backend.Model.UserPrincipal;
import com.codeBuddy.codeBuddy_Backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(){
        return userService.getUsers();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/updatePersonalInfo/{id}")
    public ResponseEntity<?>  updateUsersPersonalInfo(@PathVariable Long id, @RequestBody UsersDTO user, @AuthenticationPrincipal UserPrincipal userPrincipal){
        return userService.updateUsersPersonalInfo(id,user,userPrincipal);
    }

    @PutMapping("/updateSkills/{id}")
    public ResponseEntity<?> updateSkills(@PathVariable Long id,@RequestBody List<String> skills,@AuthenticationPrincipal UserPrincipal userPrincipal){
        return userService.updateSkills(id,skills,userPrincipal);
    }

    @PutMapping("/updateInterests/{id}")
    public ResponseEntity<?> updateInterests(@PathVariable Long id, @RequestBody List<String> interests,@AuthenticationPrincipal UserPrincipal userPrincipal){

        return userService.updateInterests(id,interests,userPrincipal);
    }


}
