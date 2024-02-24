package com.gov.iti;

import com.gov.iti.utils.EmailSender;
import com.gov.iti.utils.Hasher;
import com.gov.iti.utils.Validator;

import javax.mail.Session;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        EmailSender emailSender = EmailSender.getInstance();
        String recipient = "ahmedmosadek3@gmail.com";
        String subject = "Test Email";
        String body = "This is a test email sent from Java.";

        if (emailSender.sendEmail(recipient, subject, body)) {
            System.out.println("Email sent successfully!");
        } else {
            System.out.println("Failed to send email.");
        }
    }
}
