package com.suraj.portfolio.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.suraj.portfolio.forms.ContactForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${api.base.url}")
    private String API_BASE_URL;
    private static final String FROM_ADDRESS = "Your Portfolio <postmaster@sandbox075f1d87fa8446869614ac3f14176d4c.mailgun.org>";
    private static final String RECEIVER_EMAIL = "surajhanvate07@gmail.com";

    @Async
    public void sendEmail(ContactForm contactForm) throws UnirestException {
        logger.info("Preparing to send email from contact form submitted by: {}", contactForm.getEmail());

        String apiKey = System.getenv("API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new RuntimeException("API_KEY environment variable is not set.");
        }

        String subject = "New Message from " + contactForm.getName() + " (Portfolio Contact Form)";
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("You have received a new contact form submission:\n\n")
                .append("Name: ").append(contactForm.getName()).append("\n")
                .append("Email: ").append(contactForm.getEmail()).append("\n");

        if (contactForm.getPhone() != null && !contactForm.getPhone().trim().isEmpty()) {
            bodyBuilder.append("Phone: ").append(contactForm.getPhone()).append("\n");
        }
        bodyBuilder.append("Message:\n").append(contactForm.getMessage());

        HttpResponse<JsonNode> response = Unirest.post(API_BASE_URL)
                .basicAuth("api", apiKey)
                .queryString("from", FROM_ADDRESS)
                .queryString("to", RECEIVER_EMAIL)
                .queryString("subject", subject)
                .queryString("text", bodyBuilder.toString())
                .queryString("h:Reply-To", contactForm.getEmail())
                .asJson();

        if (response.getStatus() == 200) {
            logger.info("Email sent successfully to: {}", RECEIVER_EMAIL);
        } else {
            logger.error("Failed to send email. Status: {}, Body: {}", response.getStatus(), response.getBody());
        }
    }
}
