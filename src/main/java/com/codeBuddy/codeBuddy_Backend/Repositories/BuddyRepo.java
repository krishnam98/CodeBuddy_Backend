package com.codeBuddy.codeBuddy_Backend.Repositories;


import com.codeBuddy.codeBuddy_Backend.Model.Buddies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BuddyRepo extends JpaRepository<Buddies,Long> {
    boolean existsByUserIdAndBuddyId(Long userId, Long BuddyId);

    List<Buddies> findByUserId(Long userId);
}
