package com.codeBuddy.codeBuddy_Backend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSignUpDTO {

    private String name;
    private String username;
    private String password;
    private String email;

}
