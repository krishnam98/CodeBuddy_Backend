package com.codeBuddy.codeBuddy_Backend.Services;

import com.codeBuddy.codeBuddy_Backend.DTOs.UserSignUpDTO;
import com.codeBuddy.codeBuddy_Backend.Model.Users;
import com.codeBuddy.codeBuddy_Backend.Repositories.EmailRepo;
import com.codeBuddy.codeBuddy_Backend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private Users user;
    String skillsArr[]={"React","Redux","JavaScript","SpringBoot","Java"};
    String interestsArr[]={"Web Development","Frontend Development","React development","Java Development","Backend development"};

//    public Users getUser() {
//        user.setUsername("Krishnam2476");
//        user.setName("Krishnam Soni");
//        user.setEmail("sonikrishnam98@gmail.com");
//        user.setBio("java Developer");
//        user.setSkills(skillsArr);
//        user.setInterests(interestsArr);
//        user.setLocation("Bhopal,IN");
//        return user;
//
//    }

    public ResponseEntity<?> addUser(UserSignUpDTO userDetails) {
        String userEmail= userDetails.getEmail();

       boolean exists= emailRepo.existsByEmail(userEmail);

       if(exists){
           try{
               Users user= new Users();
               user.setName(userDetails.getName());
               user.setUsername(userDetails.getUsername());
               user.setEmail(userDetails.getEmail());
               user.setPassword(userDetails.getPassword());

               userRepo.save(user);
               return new ResponseEntity<>("User Added", HttpStatus.CREATED);

           }
           catch (Exception e){

               System.out.println(e);
               return new ResponseEntity<>("User Exists", HttpStatus.BAD_REQUEST);
           }

       }

        return new ResponseEntity<>("Email not verified!, Please verify your email to Sign Up", HttpStatus.BAD_REQUEST);
    }
}
