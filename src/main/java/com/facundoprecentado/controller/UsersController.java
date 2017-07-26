package com.facundoprecentado.controller;

import com.facundoprecentado.domain.Asociado;
import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.AsociadoRepository;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import com.facundoprecentado.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Collection<User> getUsers() {
        Collection<User> clients = userService.findAll();
        return clients;
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        if(!isUserRegistered(user)) {
            try {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                if(user.getType() == 0) { // Es SOCIO
                    mailService.sendNewSocioMail(user);
                }
                if(user.getType() == 1) { // Es ASOCIADO
                    mailService.sendNewAsociadoMail(user);
                }

                return "registration-success";
            } catch (Error e) {
                return "error";
            }
        } else {
            return "registration-fail";
        }

    }

    /*
     * Busco un Asoaciado por su ID
     */
    @RequestMapping(value = "/asociados/{idAsociado}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Asociado getAsociadoById(@PathVariable(value="idAsociado") Long idAsociado) {
        Asociado asociado = asociadoRepository.findById(idAsociado);

        return asociado;
    }


    private boolean isUserRegistered(User user) {
        if(userRepository.findByUsername(user.getUsername()) == null) {
            return false;
        }
        return true;
    }
}