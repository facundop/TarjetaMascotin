package com.facundoprecentado.controller;

import com.facundoprecentado.domain.Partner;
import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.PartnerRepository;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @PostMapping("/user/registration-success")
    public String userRegisterSuccess(@ModelAttribute User user) {
        log.info("userRegisterSuccess");
        // Verifico si el usuario existe
        User registeredUser = userRepository.findByEmail(user.getEmail());
        log.info("Verificando si existe el usuario: " + user.getEmail());
        if(registeredUser == null) {
            log.info("Registrando el usuario: " + user.getEmail());
            // Creo usuario
            userRepository.save(new User(user.getFirstName(), user.getLastName(), user.getDni(), user.getEmail(), user.getPassword(), user.isEnabled()));

            // Envio mail de notificacion a Tarjeta Mascotin
            mailService.sendNewUserMail(user);

            // TODO Envio mail de notificacion al usuario

        } else {
            log.info("El usuario ya existe: " + user.getEmail());
            return "registration-failed";
        }

        return "registration-success";
    }

    @RequestMapping("/partner/recover")
    public String partnerRecover(Partner partner) {
        log.info("partnerRecover");
        return "partner-recover";
    }

    @PostMapping("/partner/recover-success")
    public String partnerRecoverSuccess(@ModelAttribute Partner partner) {
        log.info("partnerRecoverSuccess");
        Partner recoverPartner = partnerRepository.findByEmail(partner.getEmail());
        log.info("Recuperando la contraseña de: " + partner.getEmail());
        if(recoverPartner != null) {
            log.info("Enviando la contraseña de: " + recoverPartner);
            mailService.sendPartnerPassword(recoverPartner);
        }
        return "recover-success";
    }

    @RequestMapping("/partner/registration")
    public String partnerRegister(Partner partner) {
        log.info("partnerRegister");
        return "partner-registration";
    }

    @PostMapping("/partner/registration-success")
    public String partnerRegisterSuccess(@ModelAttribute Partner partner) {
        log.info("partnerRegisterSuccess");
        // Verifico si el usuario existe
        log.info("Verificando si existe el usuario: " + partner.getEmail());
        Partner registeredPartner = partnerRepository.findByEmail(partner.getEmail());
        if(registeredPartner == null) {
            log.info("Registrando el usuario: " + partner.getEmail());
            // Creo usuario
            partnerRepository.save(new Partner(partner.getCompanyName(), partner.getEmail(), partner.getPassword()));

            // Envio mail de notificacion a Tarjeta Mascotin
            mailService.sendNewPartnerMail(partner);

            // TODO Envio mail de notificacion al usuario

        } else {
            log.info("El usuario ya existe: " + partner.getEmail());
            return "registration-failed";
        }

        return "registration-success";
    }

    @PostMapping("/user/user-login")
    public String userLogin(@ModelAttribute User user) {
        log.info("userLogin");
        // Verifico si el usuario existe
        User registeredUser = userRepository.findByEmail(user.getEmail());
        log.info("Verificando si existe el usuario: " + user.getEmail());
        if(registeredUser == null) {
            // TODO si el usuario es nulo, no esta registrado. Mandarlo al login otra vez
            return "registration-failed";
        }

        log.info("Usuario logeado: " + user.getEmail());

        return "user-logged";
    }
}
