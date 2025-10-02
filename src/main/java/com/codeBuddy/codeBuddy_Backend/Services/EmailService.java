package com.codeBuddy.codeBuddy_Backend.Services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    public boolean sendOTP(int otp,String email){

        try {
            MimeMessage message= mailSender.createMimeMessage();
            MimeMessageHelper helper= new MimeMessageHelper(message,true,"UTF-8");
            helper.setTo(email);
            helper.setSubject("Code Buddy Email Verification Code");

            String htmlContent="""
                <html>
                <body style="font-family: Arial, sans-serif; text-align: center; background: #f9f9f9; padding: 20px;">
                    <div style="background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px #ccc;">
                        <img src='cid:logoImage' width="300" style="margin-bottom: 20px;" />
                        <h2 style="color: #333;">Your OTP Code</h2>
                        <p style="font-size: 18px; color: #555;">Use the code below to verify your email:</p>
                        <h1 style="color: #007BFF;">""" + otp + """
                        </h1>
                        <p style="font-size: 14px; color: #888;">This OTP will expire in 5 minutes.</p>
                    </div>
                </body>
                </html>
                """;

            helper.setText(htmlContent,true);
            helper.addInline("logoImage", new java.io.File("src/main/resources/static/logo.png"));
            mailSender.send(message);

            return true;

        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
