package com.facundoprecentado.controller;

import com.facundoprecentado.domain.Asociado;
import com.facundoprecentado.domain.Socio;
import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.AsociadoRepository;
import com.facundoprecentado.repository.SocioRepository;
import com.facundoprecentado.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private SocioRepository socioRepository;

    @RequestMapping("/login")
    public String login(Model model, Principal principal) {
        model.addAttribute("user", new User());

        if(principal != null) { // Esta logeado
            log.info("principal " + principal.getName());
            User user = userRepository.findByUsername(principal.getName());
            if(user.getType() == 0) { // Es Socio
                Socio socio = socioRepository.findByUsername(user.getUsername());
                if(socio == null) { // No tiene nada guardado
                    socio = new Socio();
                    socio.setUsername(user.getUsername());
                }

                model.addAttribute("socio", socio);

                return "logged-socio";
            } else if(user.getType() == 1) { // Es Asociado
                Asociado asociado = asociadoRepository.findByUsername(user.getUsername());
                if(asociado == null) { // No tiene nada guardado
                    asociado = new Asociado();
                    asociado.setUsername(user.getUsername());
                }

                model.addAttribute("asociado", asociado);

                return "logged-asociado";
            }
        } else {
            log.info("not logged");
        }

        return "login";
    }
}
