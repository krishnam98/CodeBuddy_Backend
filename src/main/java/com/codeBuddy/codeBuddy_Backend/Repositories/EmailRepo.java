package com.codeBuddy.codeBuddy_Backend.Repositories;

import com.codeBuddy.codeBuddy_Backend.Model.VerifiedEmails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepo extends JpaRepository<VerifiedEmails, Long> {

    boolean existsByEmail(String userEmail);
}
