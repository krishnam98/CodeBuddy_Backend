package com.codeBuddy.codeBuddy_Backend.Services;

import com.codeBuddy.codeBuddy_Backend.DTOs.BuddyRequestDTO;
import com.codeBuddy.codeBuddy_Backend.DTOs.CreateBuddyRequestDTO;
import com.codeBuddy.codeBuddy_Backend.DTOs.UsersDTO;
import com.codeBuddy.codeBuddy_Backend.Exception.BadRequestException;
import com.codeBuddy.codeBuddy_Backend.Exception.ResourceNotFoundException;
import com.codeBuddy.codeBuddy_Backend.Model.*;
import com.codeBuddy.codeBuddy_Backend.Repositories.BuddyRepo;
import com.codeBuddy.codeBuddy_Backend.Repositories.BuddyRequestRepo;
import com.codeBuddy.codeBuddy_Backend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuddyRequestService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BuddyRequestRepo buddyRequestRepo;

    @Autowired
    private BuddyRepo buddyRepo;

    @Autowired
    private UserService userService;


    public ResponseEntity<?> createRequest(CreateBuddyRequestDTO toUser, UserPrincipal userPrincipal) {
        Optional optionalUser= userRepo.findById(toUser.getToUser());
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("user Not Found! ");
        }

        Long senderId=userPrincipal.getUser().getId();
        Long receiverId= toUser.getToUser();

        if(buddyRepo.existsByUserIdAndBuddyId(senderId,receiverId)){
            throw new BadRequestException("Already Buddies! ");
        }

        Optional optionalBuddyReq= buddyRequestRepo.findBySenderIdAndReceiverId(senderId,receiverId);

        if(optionalBuddyReq.isPresent()){
            BuddyRequest request= (BuddyRequest) optionalBuddyReq.get();
            if(request.getStatus()==BuddyRequestStatus.Pending){
                throw new BadRequestException("Request Already Created! ");
            }



        }

        try {

            BuddyRequest request= new BuddyRequest();
            request.setSender(userRepo.findById(senderId).get());
            request.setReceiver(userRepo.findById(receiverId).get());
            request.setStatus(BuddyRequestStatus.Pending);
            LocalDate date=LocalDate.now();
            request.setCreatedAt(date);
            request.setUpdatedAt(date);

            buddyRequestRepo.save(request);

            return new ResponseEntity<>("Request Sent",HttpStatus.CREATED);


        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }

    @Transactional
    public ResponseEntity<?> acceptRequest(Long reqId){
        Optional optionalReq= buddyRequestRepo.findById(reqId);

        if(optionalReq.isEmpty()){
            throw new ResourceNotFoundException("Request Not Found! ");
        }
        BuddyRequest req=(BuddyRequest) optionalReq.get();
        if(req.getStatus()!= BuddyRequestStatus.Pending){
            throw  new BadRequestException("Request already addressed! ");
        }


        Buddies buddy1= new Buddies();

        buddy1.setUser(req.getSender());
        buddy1.setBuddy(req.getReceiver());
        buddy1.setCreatedAt(LocalDate.now());

        Buddies buddy2= new Buddies();

        buddy2.setUser(req.getReceiver());
        buddy2.setBuddy(req.getSender());
        buddy2.setCreatedAt(LocalDate.now());

        req.setStatus(BuddyRequestStatus.Accepted);
        req.setUpdatedAt(LocalDate.now());

        buddyRequestRepo.save(req);
        buddyRepo.save(buddy1);
        buddyRepo.save(buddy2);

        return new ResponseEntity<>("Request Accepted! ", HttpStatus.OK);



    }

    public ResponseEntity<?> rejectRequest(Long reqId){
        Optional optionalReq= buddyRequestRepo.findById(reqId);
        if(optionalReq.isEmpty()){
            throw new ResourceNotFoundException("Request Not Found! ");
        }

        BuddyRequest req= (BuddyRequest) optionalReq.get();

        if(req.getStatus()!=BuddyRequestStatus.Pending){
            throw new BadRequestException("Request already Addressed! ");
        }

        req.setStatus(BuddyRequestStatus.Rejected);
        req.setUpdatedAt(LocalDate.now());

        buddyRequestRepo.save(req);
        return new ResponseEntity<>("Request Rejected, Poor Fellow! ",HttpStatus.OK);
    }


    public ResponseEntity<?> getIncomingRequest(Users user) {
        List<BuddyRequest> incomingRequest= buddyRequestRepo.findByReceiverIdAndStatus(user.getId(), BuddyRequestStatus.Pending);
        List<BuddyRequestDTO> buddyRequestDTOList= convertToDTOList(incomingRequest);

        return new ResponseEntity<>(buddyRequestDTOList,HttpStatus.OK);

    }

    private List<BuddyRequestDTO> convertToDTOList(List<BuddyRequest> incomingRequests){
        List<BuddyRequestDTO> dtoList= new ArrayList<>();
        for(BuddyRequest req: incomingRequests){
            BuddyRequestDTO dto= new BuddyRequestDTO();

            dto.setId(req.getId());

            UsersDTO senderDTO=userService.convertToSingleUserDTO(req.getSender());
            dto.setSender(senderDTO);

            dto.setCreatedAt(req.getCreatedAt());
            dto.setUpdatedAt(req.getUpdatedAt());

            dtoList.add(dto);

        }

        return dtoList;
    }
}

