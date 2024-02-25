package com.gov.iti.business.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {
    private static final String HOST = "smtp.gmail.com";
    private static final String EMAIL = "fruitables2024@gmail.com";
    private static final String PASSWORD = "wgav tjvq dgck ehqa ";
    private static volatile EmailSender instance = null;

    private EmailSender() {
    }

    public static EmailSender getInstance() {
        if (instance == null) {
            synchronized (EmailSender.class) {
                if (instance == null) {
                    instance = new EmailSender();
                }
            }
        }
        return instance;
    }

    public boolean sendEmail(String recipient, String subject, String body) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", "587");

        System.out.println("Properties created");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });
        System.out.println("Session created");
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
