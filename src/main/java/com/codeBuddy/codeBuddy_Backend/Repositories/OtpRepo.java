package com.codeBuddy.codeBuddy_Backend.Repositories;

import com.codeBuddy.codeBuddy_Backend.Model.TempOTPBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepo extends JpaRepository<TempOTPBook,String>{
}
