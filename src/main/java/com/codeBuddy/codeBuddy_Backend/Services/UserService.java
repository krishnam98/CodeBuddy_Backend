package com.codeBuddy.codeBuddy_Backend.Services;

import com.codeBuddy.codeBuddy_Backend.DTOs.AuthRequest;
import com.codeBuddy.codeBuddy_Backend.DTOs.AuthResponse;
import com.codeBuddy.codeBuddy_Backend.DTOs.UserSignUpDTO;
import com.codeBuddy.codeBuddy_Backend.Model.Users;
import com.codeBuddy.codeBuddy_Backend.Repositories.EmailRepo;
import com.codeBuddy.codeBuddy_Backend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private Users user;


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

    public ResponseEntity<?> verifyUser(AuthRequest user){
        Authentication authentication= authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        Users authenticatedUser= (Users)authentication.getPrincipal();

        if(authentication.isAuthenticated()){
           String accessToken= jwtService.generateAccessToken(authenticatedUser);
           String refreshtoken= jwtService.generateRefreshToken(authenticatedUser);

           return new ResponseEntity<>(new AuthResponse(accessToken,refreshtoken),HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }
}
