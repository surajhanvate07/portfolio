package com.suraj.portfolio.service;

import com.suraj.portfolio.forms.ContactForm;
import org.slf4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private static final String RECEIVER_EMAIL = "surajhanvate07@gmail.com";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(ContactForm contactForm) {
        logger.info("Preparing to send email from contact form submitted by: {}", contactForm.getEmail());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(RECEIVER_EMAIL);
        message.setSubject("New Message from " + contactForm.getName() + " (Portfolio Contact Form)");

        // Set from (optional, depending on your SMTP config)
        message.setFrom(RECEIVER_EMAIL);

        // Set reply-to to the submitter's email
        message.setReplyTo(contactForm.getEmail());

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("You have received a new contact form submission:\n\n")
                .append("Name: ").append(contactForm.getName()).append("\n")
                .append("Email: ").append(contactForm.getEmail()).append("\n");

        if (contactForm.getPhone() != null && !contactForm.getPhone().trim().isEmpty()) {
            bodyBuilder.append("Phone: ").append(contactForm.getPhone()).append("\n");
        }

        bodyBuilder.append("Message:\n").append(contactForm.getMessage());

        message.setText(bodyBuilder.toString());
        mailSender.send(message);
        logger.info("Email sent successfully to: {}", RECEIVER_EMAIL);
    }

}
