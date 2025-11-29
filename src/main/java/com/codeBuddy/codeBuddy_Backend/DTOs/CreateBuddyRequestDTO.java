package com.codeBuddy.codeBuddy_Backend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBuddyRequestDTO {

    private Long toUser;
}
