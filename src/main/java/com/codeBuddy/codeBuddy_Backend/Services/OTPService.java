package com.codeBuddy.codeBuddy_Backend.Services;

import com.codeBuddy.codeBuddy_Backend.DTOs.OTPs;
import com.codeBuddy.codeBuddy_Backend.Model.TempOTPBook;
import com.codeBuddy.codeBuddy_Backend.Model.VerifiedEmails;
import com.codeBuddy.codeBuddy_Backend.Repositories.EmailRepo;
import com.codeBuddy.codeBuddy_Backend.Repositories.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OTPService {
    @Autowired
    private EmailService emailService;

    private OtpRepo otpRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    public OTPService(OtpRepo otpRepo){
        this.otpRepo=otpRepo;
    }

    public boolean getOtp(String email) {

//        Creating 4 Digit Random Number
        SecureRandom random = new SecureRandom();
        int otp= 1000+ random.nextInt(9000);
        CharSequence cs= String.valueOf(otp);

//        Hashing it!
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        String hashedOTP=encoder.encode(cs);
        LocalDateTime currTime= LocalDateTime.now();

//        Saving the OTP-Email Pair
        TempOTPBook otpBook= new TempOTPBook();

        otpBook.setOtp(hashedOTP);
        otpBook.setEmail(email);
        otpBook.setExpireTime(currTime.plusMinutes(5));

        otpRepo.save(otpBook);


        return emailService.sendOTP(otp,email);



    }

    public HttpStatus verifyOTP(OTPs otpPair) {
//        Finding OTP-Email Pair
        Optional<TempOTPBook> otpDataOpt= otpRepo.findById(otpPair.getEmail());

//        Email didn't Exists
        if(otpDataOpt.isEmpty()){
            return HttpStatus.NOT_FOUND;
        }

//        Verifying OTP
        TempOTPBook otpData= otpDataOpt.get();
        LocalDateTime now= LocalDateTime.now();

//      Check if OTP Expired
        if(now.isAfter(otpData.getExpireTime())){
            otpRepo.deleteById(otpPair.getEmail());
            return HttpStatus.FORBIDDEN;
        }

//        Comparing OTP given by user and saved OTP
        int otp= otpPair.getOtp();
        CharSequence cs= String.valueOf(otp);
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
       boolean res= encoder.matches(cs,otpData.getOtp());

//        If everything Goes Fine!
       if(res){
//           Delete OTP-Email Pair
           otpRepo.deleteById(otpPair.getEmail());

//           Save Email in Verified Email set
           VerifiedEmails verifiedEmail= new VerifiedEmails();
           verifiedEmail.setEmail(otpPair.getEmail());
           emailRepo.save(verifiedEmail);

//           Voila!
           return HttpStatus.ACCEPTED;

       }else{

//           Ah Oh!
           otpRepo.deleteById(otpPair.getEmail());
           return HttpStatus.BAD_REQUEST;
       }


    }
}
