package com.suraj.portfolio.controller;

import com.suraj.portfolio.forms.ContactForm;
import com.suraj.portfolio.service.EmailService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

   private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

   private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("title", "Contact");
        model.addAttribute("contactForm", new ContactForm());
        return "master";
    }

    @PostMapping("/contact")
    public String handleContactFormSubmission(@Valid ContactForm contactForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("Contact form has errors: {}", bindingResult.getAllErrors());
            model.addAttribute("title", "Contact");
            return "master";
        }

        logger.info("Contact form submitted successfully: Name={}, Email={}, Phone={}, Message={}",
                contactForm.getName(), contactForm.getEmail(), contactForm.getPhone(), contactForm.getMessage());

        model.addAttribute("successMessage", "Thanks for reaching out! We'll get back to you soon.");
        model.addAttribute("contactForm", new ContactForm());
        model.addAttribute("title", "Contact");

        emailService.sendEmail(contactForm);

        return "master";
    }
}
