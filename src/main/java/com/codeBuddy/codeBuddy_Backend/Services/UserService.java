package com.codeBuddy.codeBuddy_Backend.Services;


import com.codeBuddy.codeBuddy_Backend.DTOs.UsersDTO;
import com.codeBuddy.codeBuddy_Backend.Model.Users;
import com.codeBuddy.codeBuddy_Backend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<UsersDTO> convertToUserDTO(List<Users> usersList){
        List<UsersDTO> usersDTOList= new ArrayList<>();
     for (Users user:usersList){
         UsersDTO userDTO= new UsersDTO();
         userDTO.setId(user.getId());
         userDTO.setName(user.getName());
         userDTO.setUsername(user.getUsername());
         userDTO.setEmail(user.getEmail());
         userDTO.setBio(user.getBio());
         userDTO.setCollabs(user.getCollabs());
         userDTO.setInterests(user.getInterests());
         userDTO.setLocation(user.getLocation());
         userDTO.setMobileNumber(user.getMobileNumber());
         userDTO.setSkills(user.getSkills());
         userDTO.setRating(user.getRating());

         usersDTOList.add(userDTO);
     }

     return usersDTOList;
    }

    public ResponseEntity<?> getUsers() {
        try {
            List<Users> usersList= userRepo.findAll();
            return new ResponseEntity<>(convertToUserDTO(usersList), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
