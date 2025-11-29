package com.codeBuddy.codeBuddy_Backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class BuddyDTO {
    private Long buddyId;
    private Long buddyUser_id;
    private String buddyUser_ProfileURL;
    private String buddyUser_name;
    private String buddyUser_username;
    private LocalDate buddySince;

}
