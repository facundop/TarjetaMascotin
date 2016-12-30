package com.facundoprecentado.controller;

import com.facundoprecentado.domain.Guest;
import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/services")
    public String services() {
        return "services";
    }

    @RequestMapping("/who")
    public String who() {
        return "who";
    }

    @RequestMapping("/contact")
    public String contact(Guest guest) {
        return "contact";
    }

    @PostMapping("/contact-success")
    public String contactSuccess(@ModelAttribute Guest guest) {
        mailService.sendContactMail(guest);
        return "contact-success";
    }

}