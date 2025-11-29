package com.codeBuddy.codeBuddy_Backend.DTOs;

import com.codeBuddy.codeBuddy_Backend.Model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuddyRequestDTO {
    private Long id;
    private UsersDTO sender;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
