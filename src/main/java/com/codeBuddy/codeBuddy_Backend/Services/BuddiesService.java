package com.codeBuddy.codeBuddy_Backend.Services;

import com.codeBuddy.codeBuddy_Backend.DTOs.BuddyDTO;
import com.codeBuddy.codeBuddy_Backend.Model.Buddies;
import com.codeBuddy.codeBuddy_Backend.Model.Users;
import com.codeBuddy.codeBuddy_Backend.Repositories.BuddyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuddiesService {
    @Autowired
    private BuddyRepo buddyRepo;

    public List<BuddyDTO> convertToBuddyDTO(List<Buddies> buddies){
        List<BuddyDTO> buddyDTOList= new ArrayList<>();
        for(Buddies buddy:buddies){
            BuddyDTO buddyDTO= new BuddyDTO();

            buddyDTO.setBuddyId(buddy.getId());

            buddyDTO.setBuddyUser_id(buddy.getBuddy().getId());
            buddyDTO.setBuddyUser_ProfileURL(buddy.getBuddy().getProfileImgURL());
            buddyDTO.setBuddyUser_name(buddy.getBuddy().getName());
            buddyDTO.setBuddyUser_username(buddy.getBuddy().getUsername());

            buddyDTO.setBuddySince(buddy.getCreatedAt());

            buddyDTOList.add(buddyDTO);
        }

        return buddyDTOList;

    }

    public ResponseEntity<?> getBuddies(Users user) {
        List<Buddies> buddies= buddyRepo.findByUserId(user.getId());
        List<BuddyDTO> buddyDTOList= convertToBuddyDTO(buddies);

        return new ResponseEntity<>(buddyDTOList, HttpStatus.OK);
    }
}
