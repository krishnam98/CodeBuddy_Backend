package com.codeBuddy.codeBuddy_Backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuddyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private Users sender;

    @ManyToOne
    @JoinColumn(name="receiver_id")
    private Users receiver;

    private LocalDate createdAt;

    private  LocalDate updatedAt;

    @Enumerated(EnumType.STRING)
    private BuddyRequestStatus status;


}
