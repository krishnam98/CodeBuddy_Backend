package com.codeBuddy.codeBuddy_Backend.Repositories;

import com.codeBuddy.codeBuddy_Backend.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
