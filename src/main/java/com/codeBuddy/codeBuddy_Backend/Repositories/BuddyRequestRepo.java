package com.codeBuddy.codeBuddy_Backend.Repositories;

import com.codeBuddy.codeBuddy_Backend.Model.BuddyRequest;
import com.codeBuddy.codeBuddy_Backend.Model.BuddyRequestStatus;
import io.jsonwebtoken.security.Jwks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuddyRequestRepo extends JpaRepository<BuddyRequest,Long> {

    Optional<BuddyRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<BuddyRequest> findByReceiverIdAndStatus(Long receiver, BuddyRequestStatus status);

}
