package com.codeBuddy.codeBuddy_Backend.Controllers;

import com.codeBuddy.codeBuddy_Backend.DTOs.OTPs;
import com.codeBuddy.codeBuddy_Backend.Services.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify")
@CrossOrigin(origins = "*")
public class VerificationController {

    @Autowired
    private OTPService otpService;

    @PostMapping("/email")
    public ResponseEntity<?> generateOTP(@RequestBody String email){
        boolean otp_status=otpService.getOtp(email);

        if(otp_status){
            return new ResponseEntity<>("OTP Sent",HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/OTP")
    public ResponseEntity<?> VerifyOTP(@RequestBody OTPs otpPair){
         return new ResponseEntity (otpService.verifyOTP(otpPair));
    }

}
