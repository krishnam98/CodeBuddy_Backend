package com.codeBuddy.codeBuddy_Backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;
    private String mobileNumber;
    private String location;
    private String bio;

    @ElementCollection
    private List<String> skills= new ArrayList<>();

    @ElementCollection
    private List<String> interests= new ArrayList<>();
    private float rating;
    private int collabs;

}
