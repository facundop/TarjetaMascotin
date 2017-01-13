package com.facundoprecentado.controller;

import com.facundoprecentado.domain.*;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Optional;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/")
    public String index() {
        log.info("index");
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        log.info("home");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", auth.getPrincipal());
        log.info("Authenticated: " + auth.isAuthenticated());
        log.info("User: " + auth.getPrincipal().toString());
        return "home";
    }

    @RequestMapping("/plans")
    public String plans() {
        log.info("plans");
        return "plans";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        log.info("GET login");
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        log.info("GET registration");
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        log.info("registration");
        log.info("Registrando usuario: " + user.getUsername());

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Error e) {
            return "error";
        }

        // TODO comprobar que se registre y en caso de error actuar

        return "registration-success";
    }

    @RequestMapping("/contacts")
    public String contact(Model model) {
        log.info("contacts");
        model.addAttribute("guest", new Guest());
        return "contacts";
    }

    @PostMapping("/contacts")
    public String contactSuccess(@ModelAttribute Guest guest) {
        log.info("POST contacts");
        log.info("Enviando: " + guest.getEmail() + " " + guest.getMessage());
        // mailService.sendContactMail(guest);
        return "contacts-success";
    }

    @RequestMapping("/recover")
    public String passwordRecover(Model model) {
        log.info("passwordRecover");
        model.addAttribute("user", new User());
        return "password-recover";
    }

    @PostMapping("/recover")
    public String userRecoverSuccess(@ModelAttribute User user) {
        log.info("POST recover");

        User recoverUser = userRepository.findByUsername(user.getUsername());
        log.info("Recuperando la contraseña de: " + user.getUsername());
        if(recoverUser != null) {
            log.info("Enviando la contraseña de: " + recoverUser);
            mailService.sendUserPassword(recoverUser);
        }

        return "password-recover-success";
    }

}