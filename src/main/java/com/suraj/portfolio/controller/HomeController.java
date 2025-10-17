package com.suraj.portfolio.controller;

import com.suraj.portfolio.forms.ContactForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"", "/", "/home"})
    public String showHomePage(Model model) {
        model.addAttribute("title", "Home");
        return "master";
    }

    @GetMapping("/projects")
    public String showProjectsPage(Model model) {
        model.addAttribute("title", "Projects");
        return "master";
    }

    @GetMapping("/resume")
    public String showResumePage(Model model) {
        model.addAttribute("title", "Resume");
        return "master";
    }
}
