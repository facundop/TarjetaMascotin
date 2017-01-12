package com.facundoprecentado.controller;

import com.facundoprecentado.domain.Guest;
import com.facundoprecentado.domain.Partner;
import com.facundoprecentado.domain.PasswordRecover;
import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.PartnerRepository;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @RequestMapping("/")
    public String index() {
        log.info("index");
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        log.info("home");
        return "home";
    }

    @RequestMapping("/plans")
    public String plans() {
        log.info("plans");
        return "plans";
    }

    @RequestMapping("/user")
    public String user(User user) {
        log.info("user");
        return "user";
    }

    @RequestMapping("/partner")
    public String partner(Partner partner) {
        log.info("partner");
        return "partner";
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
        // mailService.sendContactMail(guest);
        return "contacts-success";
    }

    @RequestMapping("/recover")
    public String passwordRecover(Model model) {
        log.info("passwordRecover");
        model.addAttribute("passwordRecover", new PasswordRecover());
        return "password-recover";
    }

    @PostMapping("/recover")
    public String userRecoverSuccess(@ModelAttribute PasswordRecover passwordRecover) {
        log.info("POST recover");

        if(passwordRecover.getType() == 0) { // 0 = User
            User recoverUser = userRepository.findByEmail(passwordRecover.getEmail());
            log.info("Recuperando la contraseña de: " + passwordRecover.getEmail());
            if(recoverUser != null) {
                log.info("Enviando la contraseña de: " + recoverUser);
                mailService.sendUserPassword(recoverUser);
            }
        } else if(passwordRecover.getType() == 1) { // 1 = Negocios
            Partner recoverPartner = partnerRepository.findByEmail(passwordRecover.getEmail());
            log.info("Recuperando la contraseña de: " + passwordRecover.getEmail());
            if(recoverPartner != null) {
                log.info("Enviando la contraseña de: " + recoverPartner);
                mailService.sendPartnerPassword(recoverPartner);
            }
        }
        return "password-recover-success";
    }

}