package com.star.journalApp;

import com.star.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {
    @Autowired
    private EmailService emailService;
    @Test
    void testSender(){
        emailService.sendEmail("khatarnakgaming6969@gmail.com",
                "Testing JAVA mail sender",
                "Hii...app kaise ho");

    }
}
