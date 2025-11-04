package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.DTOs.UsersDTO;
import com.codeBuddy.codeBuddy_Backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/updatePersonalInfo")
    public ResponseEntity<?>  updateUsersPersonalInfo(@RequestBody UsersDTO user){
        return userService.updateUsersPersonalInfo(user);
    }

    @PutMapping("/updateSkills/{id}")
    public ResponseEntity<?> updateSkills(@PathVariable Long id,@RequestBody List<String> skills){
        return userService.updateSkills(id,skills);
    }

    @PutMapping("/updateInterests/{id}")
    public ResponseEntity<?> updateInterests(@PathVariable Long id, @RequestBody List<String> interests){
        return userService.updateInterests(id,interests);
    }


}
