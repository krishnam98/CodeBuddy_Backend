package com.codeBuddy.codeBuddy_Backend.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TempOTPBook {
    @Id
    private String email;
    private String otp;
    private LocalDateTime expireTime;

}
