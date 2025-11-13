package com.codeBuddy.codeBuddy_Backend.Services;

import com.codeBuddy.codeBuddy_Backend.DTOs.AuthRequest;
import com.codeBuddy.codeBuddy_Backend.DTOs.AuthResponse;
import com.codeBuddy.codeBuddy_Backend.DTOs.UserSignUpDTO;
import com.codeBuddy.codeBuddy_Backend.DTOs.UsersDTO;
import com.codeBuddy.codeBuddy_Backend.Model.UserPrincipal;
import com.codeBuddy.codeBuddy_Backend.Model.Users;
import com.codeBuddy.codeBuddy_Backend.Repositories.EmailRepo;
import com.codeBuddy.codeBuddy_Backend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Service
public class AuthService {
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

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);


    public ResponseEntity<?> addUser(UserSignUpDTO userDetails) {
        String userEmail= userDetails.getEmail();

       boolean exists= emailRepo.existsByEmail(userEmail);

       if(exists){
           try{
               Users user= new Users();
               user.setName(userDetails.getName());
               user.setUsername(userDetails.getUsername());
               user.setEmail(userDetails.getEmail());
               user.setPassword(encoder.encode(userDetails.getPassword()));
               LocalDate today= LocalDate.now();
               user.setJoinDate(today);


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
        UserPrincipal authenticatedUserPrincipal= (UserPrincipal) authentication.getPrincipal();
        Users authenticatedUser=authenticatedUserPrincipal.getUser();

        if(authentication.isAuthenticated()){
           String accessToken= jwtService.generateAccessToken(authenticatedUser);
           String refreshtoken= jwtService.generateRefreshToken(authenticatedUser);
           System.out.println(authenticatedUser);
            UsersDTO usersDTO= userService.convertToSingleUserDTO(authenticatedUser);


           return new ResponseEntity<>(new AuthResponse(accessToken,refreshtoken,usersDTO),HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<?> refreshAccessToken(Map<String, String> token) {
        String refreshToken=token.get("refreshToken");
        if(refreshToken==null){
            return new ResponseEntity<>("Refresh Token Not Found", HttpStatus.BAD_REQUEST);
        }
        String username="";

        try{
//-------------Extracting Username------------------------
            username= jwtService.extractUsername(refreshToken);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Token!\nLogin Required ",HttpStatus.UNAUTHORIZED);
        }

        try {

//-------------Loading user and UserDetails----------------

            Users user= userRepo.findByUsername(username);
            UserDetails userDetails= myUserDetailsService.loadUserByUsername(username);

//-------------Validating Refresh Token--------------------
            if (jwtService.validateToken(refreshToken,userDetails)){

//-------------Generating new Token-----------------------
                String newAccessToken= jwtService.generateAccessToken(user);
//-------------Sending new access Token------------------

                return new ResponseEntity<>(Map.of("accessToken",newAccessToken),HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Invalid Token!\nLogin Required ",HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>("Invalid Token!\nLogin Required ",HttpStatus.UNAUTHORIZED);
        }


    }
}
