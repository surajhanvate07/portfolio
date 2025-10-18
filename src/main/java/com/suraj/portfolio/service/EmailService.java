package com.suraj.portfolio.service;

import com.suraj.portfolio.forms.ContactForm;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private static final String RECEIVER_EMAIL = "surajhanvate07@gmail.com";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(ContactForm contactForm) throws Exception {
        logger.info("Preparing to send email from contact form submitted by: {}", contactForm.getEmail());
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        // Set from: display name and email address
        helper.setFrom(new InternetAddress(RECEIVER_EMAIL, "Your Portfolio"));
        helper.setTo(RECEIVER_EMAIL);
        helper.setReplyTo(contactForm.getEmail());
        helper.setSubject("New Message from " + contactForm.getName() + " (Portfolio Contact Form)");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("You have received a new contact form submission:\n\n")
                .append("Name: ").append(contactForm.getName()).append("\n")
                .append("Email: ").append(contactForm.getEmail()).append("\n");

        if (contactForm.getPhone() != null && !contactForm.getPhone().trim().isEmpty()) {
            bodyBuilder.append("Phone: ").append(contactForm.getPhone()).append("\n");
        }
        bodyBuilder.append("Message:\n").append(contactForm.getMessage());

        helper.setText(bodyBuilder.toString(), false);
        mailSender.send(mimeMessage);
        logger.info("Email sent successfully to: {}", RECEIVER_EMAIL);
    }

}
