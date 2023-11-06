package com.example.spring_project;

import com.mailjet.client.errors.MailjetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final MailjetService mailjetService;
    private final DatabaseDataInsertion databaseDataInsertion;
    @Autowired
    public EmailController(MailjetService mailjetService, DatabaseDataInsertion databaseDataInsertion) {
        this.mailjetService = mailjetService;
        this.databaseDataInsertion = databaseDataInsertion;
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendEmail() {
        try {
            mailjetService.sendEmail();
            databaseDataInsertion.addEmailsToDatabase();// Delegates the email sending to the MailjetService.
            return ResponseEntity.ok("Email sent successfully.");
        } catch (MailjetException e) {
            // Handle MailjetException
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Mailjet error: " + e.getMessage());
        }
    }
}
