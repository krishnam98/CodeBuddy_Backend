package com.codeBuddy.codeBuddy_Backend.DTOs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String mobileNumber;
    private String location;
    private String bio;
    private List<String> skills= new ArrayList<>();
    private List<String> interests= new ArrayList<>();
    private float rating;
    private int collabs;
    private String profileImgURL;
    private String coverImgURL;
    private LocalDate joinDate;
}
