package com.facundoprecentado.controller;

import com.facundoprecentado.domain.Guest;
import com.facundoprecentado.domain.User;
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

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/status")
    public String userStatus(@ModelAttribute User user) {
        return "user-status";
    }

    @PostMapping("/user/status")
    public String showUserStatus(Model model, @ModelAttribute User user) {
        User userStatus = userRepository.findByDni(user.getDni());
        if(userStatus != null) {
            log.info("El usuario existe: " + userStatus);
            model.addAttribute(userStatus);
        } else {
            log.info("El usuario no existe");
            return "user-status-info-failed";
        }

        return "user-status-info";
    }

}