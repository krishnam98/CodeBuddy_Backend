package com.codeBuddy.codeBuddy_Backend.Services;


import com.codeBuddy.codeBuddy_Backend.DTOs.UsersDTO;
import com.codeBuddy.codeBuddy_Backend.Model.UserPrincipal;
import com.codeBuddy.codeBuddy_Backend.Model.Users;
import com.codeBuddy.codeBuddy_Backend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UsersDTO convertToSingleUserDTO(Users user){
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

        return userDTO;
    }

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

    public ResponseEntity<?> getUserById(Long id) {
        Optional optionalUser= userRepo.findById(id);
        if(optionalUser.isPresent()){
            UsersDTO usersDTO= convertToSingleUserDTO((Users)optionalUser.get());
            return new ResponseEntity<>(usersDTO,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public ResponseEntity<?> updateUsersPersonalInfo(Long id,UsersDTO user, UserPrincipal userPrincipal) {
        System.out.println("entered Backend");
        System.out.println(userPrincipal.getUsername());
        System.out.println(userPrincipal.getPassword());
        if(userPrincipal.getUser().getId()!=id){
            return new ResponseEntity<>("Not Authorised",HttpStatus.UNAUTHORIZED);
        }
        Optional optional= userRepo.findById(id);

        if(optional.isPresent()){
            try {
                Users optionalUser= (Users)optional.get();
                if (user.getName() != null && !user.getName().isEmpty()) {
                    optionalUser.setName(user.getName());
                }

                if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                    optionalUser.setUsername(user.getUsername());
                }

                if (user.getMobileNumber() != null && !user.getMobileNumber().isEmpty()) {
                    optionalUser.setMobileNumber(user.getMobileNumber());
                }

                if (user.getLocation() != null && !user.getLocation().isEmpty()) {
                    optionalUser.setLocation(user.getLocation());
                }

                if (user.getBio() != null && !user.getBio().isEmpty()) {
                    optionalUser.setBio(user.getBio());
                }

                System.out.println(optionalUser);

                userRepo.save(optionalUser);
                return new ResponseEntity<>(optionalUser,HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("cannot update at this moment!",HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        else{
            return new ResponseEntity<>("No user found with this ID", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateSkills(Long id, List<String> userSkills, UserPrincipal userPrincipal) {
        if(userPrincipal.getUser().getId()!= id){
            return new ResponseEntity<>("Not Authorised",HttpStatus.UNAUTHORIZED);
        }
        Optional optional= userRepo.findById(id);

        if(optional.isPresent()){
            try{
                Users optionalUser= (Users) optional.get();
                if(userSkills!=null){
                    optionalUser.setSkills(userSkills);
                }

                userRepo.save(optionalUser);
                return new ResponseEntity<>(optionalUser,HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("cannot update at this moment", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>("No User found with this id", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updateInterests(Long id, List<String> interests, UserPrincipal userPrincipal) {
        if(userPrincipal.getUser().getId()!= id){
            return new ResponseEntity<>("Not Authorised",HttpStatus.UNAUTHORIZED);
        }
        Optional optional= userRepo.findById(id);

        if(optional.isPresent()){
            try{
                Users optionalUser= (Users) optional.get();

                if(interests!=null){
                    optionalUser.setInterests(interests);
                }

                userRepo.save(optionalUser);
                return new ResponseEntity<>(optionalUser,HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>("Cannot update at this moment", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>("No User found by this ID", HttpStatus.BAD_REQUEST);
        }
    }
}
